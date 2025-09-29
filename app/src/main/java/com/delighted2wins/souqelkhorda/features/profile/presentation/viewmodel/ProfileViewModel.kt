package com.delighted2wins.souqelkhorda.features.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.core.enums.OrderStatus
import com.delighted2wins.souqelkhorda.features.authentication.domain.useCase.FreeUserCase
import com.delighted2wins.souqelkhorda.features.authentication.domain.useCase.LogoutUseCase
import com.delighted2wins.souqelkhorda.features.buyers.domain.use_case.IsBuyersCase
import com.delighted2wins.souqelkhorda.features.history.domain.usecase.GetUserOrdersUseCase
import com.delighted2wins.souqelkhorda.features.profile.domain.entity.ProfileMessagesEnum
import com.delighted2wins.souqelkhorda.features.profile.domain.usecase.GetUserProfileUseCase
import com.delighted2wins.souqelkhorda.features.profile.domain.usecase.SetLanguageUseCase
import com.delighted2wins.souqelkhorda.features.profile.domain.usecase.UpdateUserProfileUseCase
import com.delighted2wins.souqelkhorda.features.profile.presentation.contract.ProfileContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val freeUserCase: FreeUserCase,
    private val setLanguageUseCase: SetLanguageUseCase,
    private val getUserOrdersUseCase: GetUserOrdersUseCase,
    private val isBuyersCase: IsBuyersCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileContract.State())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ProfileContract.Effect>()
    val effect = _effect.asSharedFlow()

    init {
        loadProfile()
        loadOrders()
        loadBuyerState()
    }

    fun handleIntent(intent: ProfileContract.Intent) {
        when (intent) {
            is ProfileContract.Intent.LoadProfile -> loadProfile()
            is ProfileContract.Intent.LoadOrders -> loadOrders()
            is ProfileContract.Intent.LoadBuyerState -> loadBuyerState()

            is ProfileContract.Intent.ChangeName -> changeName(intent.name)
            is ProfileContract.Intent.ChangePhone -> changePhone(intent.phone)
            is ProfileContract.Intent.ChangeGovernorate -> changeGovernorate(intent.governorate)
            is ProfileContract.Intent.ChangeArea -> changeArea(intent.area)
            is ProfileContract.Intent.ChangeAddress -> changeAddress(intent.address)
            is ProfileContract.Intent.ChangeImageUrl -> changeImageUrl(intent.imageUrl)
            is ProfileContract.Intent.SaveName -> updateName()
            is ProfileContract.Intent.SavePhone -> updatePhone()
            is ProfileContract.Intent.SaveGovernorate -> updateGovernorate()
            is ProfileContract.Intent.SaveArea -> updateArea()
            is ProfileContract.Intent.SaveAddress -> updateAddress()
            is ProfileContract.Intent.SaveImageUrl -> uploadAndUpdateUserImage()
            is ProfileContract.Intent.StartEditing -> startEditing(
                intent.fieldSelector,
                intent.fieldSetter
            )

            is ProfileContract.Intent.CancelEditing -> cancelEditing(
                intent.fieldSelector,
                intent.fieldSetter
            )

            is ProfileContract.Intent.ChangeLanguage -> setLanguageUseCase(intent.lang)

            is ProfileContract.Intent.Logout -> {
                logoutUseCase()
                freeUserCase()
                sendEffect(ProfileContract.Effect.Logout)
            }

            is ProfileContract.Intent.NavigateToHistory -> sendEffect(ProfileContract.Effect.NavigateToHistory)
        }
    }

    private fun loadProfile() {
        _state.update { it.copy(isLoadingProfile = true) }
        viewModelScope.launch(Dispatchers.IO) {
            val result = getUserProfileUseCase()
            result.onSuccess { user ->
                _state.update {
                    it.copy(
                        isLoadingProfile = false,
                        userId = user.id,
                        name = ProfileContract.ProfileFieldState(value = user.name),
                        email = ProfileContract.ProfileFieldState(value = user.email),
                        phone = ProfileContract.ProfileFieldState(value = user.phone),
                        governorate = ProfileContract.ProfileFieldState(value = user.governorate),
                        area = ProfileContract.ProfileFieldState(value = user.area),
                        address = ProfileContract.ProfileFieldState(value = user.address),
                        imageUrl = ProfileContract.ProfileFieldState(value = user.imageUrl ?: "")
                    )
                }
            }.onFailure {
                _state.update {
                    it.copy(
                        isLoadingProfile = false,
                        generalError = it.generalError
                    )
                }
            }
        }
    }

    private fun loadBuyerState() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = isBuyersCase(_state.value.userId)
            if (result) {
                _state.update { it.copy(isBuyer = true) }
            }
        }
    }

    private fun loadOrders() {
        _state.update { it.copy(isLoadingOrders = true) }
        viewModelScope.launch(Dispatchers.IO) {
            val result = getUserOrdersUseCase()
            result.onSuccess { history ->
                val orders = history.orders
                _state.update {
                    it.copy(
                        isLoadingOrders = false,
                        completedCount = orders.count { order -> order.status == OrderStatus.COMPLETED },
                        pendingCount = orders.count { order -> order.status == OrderStatus.PENDING },
                        cancelledCount = orders.count { order -> order.status == OrderStatus.CANCELLED }
                    )
                }
            }.onFailure { throwable ->
                _state.update { it.copy(isLoadingOrders = false, generalError = throwable.message) }
            }
        }
    }


    private fun changeName(newName: String) {
        _state.update { it.copy(name = it.name.copy(value = newName)) }
    }

    private fun changePhone(newPhone: String) {
        _state.update { it.copy(phone = it.phone.copy(value = newPhone)) }
    }

    private fun changeGovernorate(newGovernorate: String) {
        _state.update { it.copy(governorate = it.governorate.copy(value = newGovernorate)) }
    }

    private fun changeArea(newArea: String) {
        _state.update { it.copy(area = it.area.copy(value = newArea)) }
    }

    private fun changeAddress(newAddress: String) {
        _state.update { it.copy(address = it.address.copy(value = newAddress)) }
    }

    private fun changeImageUrl(newUrl: String) {
        _state.update { it.copy(imageUrl = it.imageUrl.copy(value = newUrl)) }
    }

    private fun updateField(
        current: ProfileContract.ProfileFieldState,
        update: suspend () -> Result<Unit>,
        setFieldValue: (ProfileContract.State, ProfileContract.ProfileFieldState) -> ProfileContract.State,
        successMessage: ProfileMessagesEnum
    ) {
        _state.update { setFieldValue(it, current.copy(isLoading = true)) }
        viewModelScope.launch(Dispatchers.IO) {
            val result = update()
            result.onSuccess {
                _state.update {
                    setFieldValue(
                        it,
                        current.copy(isEditing = false, isLoading = false, success = true, error = null)
                    )
                }
                sendEffect(ProfileContract.Effect.ShowSnackbar(successMessage.getMsg()))
            }.onFailure { throwable ->
                _state.update {
                    setFieldValue(
                        it,
                        current.copy(
                            isLoading = false,
                            error = throwable.message ?: ProfileMessagesEnum.UNKNOWN.getMsg()
                        )
                    )
                }
            }
        }
    }

    private fun startEditing(
        field: (ProfileContract.State) -> ProfileContract.ProfileFieldState,
        setFieldValue: (ProfileContract.State, ProfileContract.ProfileFieldState) -> ProfileContract.State
    ) {
        _state.update {
            val current = field(it)
            setFieldValue(
                it,
                current.copy(isEditing = true, success = false, error = null, originalValue = current.value)
            )
        }
    }

    private fun cancelEditing(
        field: (ProfileContract.State) -> ProfileContract.ProfileFieldState,
        setFieldValue: (ProfileContract.State, ProfileContract.ProfileFieldState) -> ProfileContract.State
    ) {
        _state.update {
            val current = field(it)
            setFieldValue(
                it,
                current.copy(isEditing = false, success = false, error = null, value = current.originalValue)
            )
        }
    }

    private fun updateName() {
        val name = _state.value.name
        updateField(
            name,
            { updateUserProfileUseCase.updateName(name.value) },
            { state, field -> state.copy(name = field) },
            ProfileMessagesEnum.NAME_UPDATED
        )
    }


    private fun updatePhone() {
        val phone = _state.value.phone
        updateField(
            phone,
            { updateUserProfileUseCase.updatePhone(phone.value) },
            { state, field -> state.copy(phone = field) },
            ProfileMessagesEnum.PHONE_UPDATED
        )
    }

    private fun updateGovernorate() {
        val governorate = _state.value.governorate
        updateField(
            governorate,
            { updateUserProfileUseCase.updateGovernorate(governorate.value) },
            { state, field -> state.copy(governorate = field) },
            ProfileMessagesEnum.GOVERNORATE_UPDATED
        )
    }

    private fun updateArea() {
        val area = _state.value.area
        updateField(
            area,
            { updateUserProfileUseCase.updateArea(area.value) },
            { state, field -> state.copy(area = field) },
            ProfileMessagesEnum.AREA_UPDATED
        )
    }

    private fun updateAddress() {
        val address = _state.value.address
        updateField(
            address,
            { updateUserProfileUseCase.updateAddress(address.value) },
            { state, field -> state.copy(address = field) },
            ProfileMessagesEnum.ADDRESS_UPDATED
        )
    }

    private fun uploadAndUpdateUserImage() {
        val imageUrl = _state.value.imageUrl
        updateField(
            imageUrl,
            { updateUserProfileUseCase.uploadAndUpdateUserImage(imageUrl.value) },
            { state, field -> state.copy(imageUrl = field) },
            ProfileMessagesEnum.IMAGE_UPDATED
        )
    }

    private fun sendEffect(effect: ProfileContract.Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}