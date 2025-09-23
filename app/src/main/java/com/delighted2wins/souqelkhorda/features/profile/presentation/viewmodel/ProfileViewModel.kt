package com.delighted2wins.souqelkhorda.features.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.features.authentication.domain.useCase.FreeUserCase
import com.delighted2wins.souqelkhorda.features.authentication.domain.useCase.LogoutUseCase
import com.delighted2wins.souqelkhorda.features.profile.domain.entity.ProfileMessagesEnum
import com.delighted2wins.souqelkhorda.features.profile.domain.usecase.GetUserProfileUseCase
import com.delighted2wins.souqelkhorda.features.profile.domain.usecase.SetLanguageUseCase
import com.delighted2wins.souqelkhorda.features.profile.domain.usecase.UpdateUserEmailUseCase
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
    private val updateUserEmailUseCase: UpdateUserEmailUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val freeUserCase: FreeUserCase,
    private val setLanguageUseCase: SetLanguageUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileContract.State())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ProfileContract.Effect>()
    val effect = _effect.asSharedFlow()

    init {
        loadProfile()
    }

    fun handleIntent(intent: ProfileContract.Intent) {
        when (intent) {
            is ProfileContract.Intent.LoadProfile -> loadProfile()
            is ProfileContract.Intent.ChangeName -> changeName(intent.name)
            is ProfileContract.Intent.ChangeEmail -> changeEmail(intent.email)
            is ProfileContract.Intent.ChangePhone -> changePhone(intent.phone)
            is ProfileContract.Intent.ChangeGovernorate -> changeGovernorate(intent.governorate)
            is ProfileContract.Intent.ChangeAddress -> changeAddress(intent.address)
            is ProfileContract.Intent.ChangeImageUrl -> changeImageUrl(intent.imageUrl)
            is ProfileContract.Intent.SaveName -> updateName()
            is ProfileContract.Intent.SaveEmail -> updateEmail()
            is ProfileContract.Intent.SavePhone -> updatePhone()
            is ProfileContract.Intent.SaveGovernorate -> updateGovernorate()
            is ProfileContract.Intent.SaveAddress -> updateAddress()
            is ProfileContract.Intent.SaveImageUrl -> updateImageUrl()
            is ProfileContract.Intent.StartEditing -> startEditing(intent.fieldSelector, intent.fieldSetter)
            is ProfileContract.Intent.CancelEditing -> cancelEditing(intent.fieldSelector, intent.fieldSetter)

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
                        name = ProfileContract.ProfileFieldState(value = user.name),
                        email = ProfileContract.ProfileFieldState(value = user.email),
                        phone = ProfileContract.ProfileFieldState(value = user.phone),
                        governorate = ProfileContract.ProfileFieldState(value = user.governorate),
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

    private fun changeName(newName: String) {
        _state.update { it.copy(name = it.name.copy(value = newName)) }
    }

    private fun changeEmail(newEmail: String) {
        _state.update { it.copy(email = it.email.copy(value = newEmail)) }
    }

    private fun changePhone(newPhone: String) {
        _state.update { it.copy(phone = it.phone.copy(value = newPhone)) }
    }

    private fun changeGovernorate(newGovernorate: String) {
        _state.update { it.copy(governorate = it.governorate.copy(value = newGovernorate)) }
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
        setFieldValue: (ProfileContract.State, ProfileContract.ProfileFieldState) -> ProfileContract.State
    ) {
        _state.update { setFieldValue(it, current.copy(isLoading = true)) }
        viewModelScope.launch(Dispatchers.IO) {
            val result = update()
            result.onSuccess {
                _state.update { setFieldValue(it, current.copy(isEditing = false, isLoading = false, success = true)) }
            }.onFailure { throwable ->
                _state.update { setFieldValue(it, current.copy(isLoading = false, error = throwable.message ?: ProfileMessagesEnum.UNKNOWN.getMsg())) }
            }
        }
    }

    private fun startEditing(
        field: (ProfileContract.State) -> ProfileContract.ProfileFieldState,
        setFieldValue: (ProfileContract.State, ProfileContract.ProfileFieldState) -> ProfileContract.State
    ) {
        _state.update { setFieldValue(it, field(it).copy(isEditing = true, success = false, error = null)) }
    }

    private fun cancelEditing(
        field: (ProfileContract.State) -> ProfileContract.ProfileFieldState,
        setFieldValue: (ProfileContract.State, ProfileContract.ProfileFieldState) -> ProfileContract.State
    ) {
        _state.update { setFieldValue(it, field(it).copy(isEditing = false, success = false, error = null)) }
    }

    private fun updateName() {
        val name = _state.value.name
        updateField(name, { updateUserProfileUseCase.updateName(name.value) }) { state, field ->
            state.copy(name = field)
        }
    }

    private fun updateEmail() {
        val email = _state.value.email
        updateField(email, { updateUserEmailUseCase(email.value) }) { state, field ->
            state.copy(email = field)
        }
    }

    private fun updatePhone() {
        val phone = _state.value.phone
        updateField(phone, { updateUserProfileUseCase.updatePhone(phone.value) }) { state, field ->
            state.copy(phone = field)
        }
    }

    private fun updateGovernorate() {
        val governorate = _state.value.governorate
        updateField(governorate, { updateUserProfileUseCase.updateGovernorate(governorate.value) }) { state, field ->
            state.copy(governorate = field)
        }
    }

    private fun updateAddress() {
        val address = _state.value.address
        updateField(address, { updateUserProfileUseCase.updateAddress(address.value) }) { state, field ->
            state.copy(address = field)
        }
    }

    private fun updateImageUrl() {
        val imageUrl = _state.value.imageUrl
        updateField(imageUrl, { updateUserProfileUseCase.updateImageUrl(imageUrl.value) }) { state, field ->
            state.copy(imageUrl = field)
        }
    }

    private fun sendEffect(effect: ProfileContract.Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}