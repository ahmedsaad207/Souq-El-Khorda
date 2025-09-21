package com.delighted2wins.souqelkhorda.features.profile.presentation.viewmodel

import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.features.profile.domain.usecase.GetUserProfileUseCase
import com.delighted2wins.souqelkhorda.features.profile.domain.usecase.UpdateUserEmailUseCase
import com.delighted2wins.souqelkhorda.features.profile.domain.usecase.UpdateUserProfileUseCase
import com.delighted2wins.souqelkhorda.features.profile.presentation.contract.ProfileContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateUserEmailUseCase: UpdateUserEmailUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileContract.State())
    val state: MutableStateFlow<ProfileContract.State> = _state

    init {
        loadProfile()
    }

    fun handleIntent(intent: ProfileContract.Intent) {
        when (intent) {
            is ProfileContract.Intent.LoadProfile -> loadProfile()
            is ProfileContract.Intent.UpdateName -> updateName()
            is ProfileContract.Intent.UpdateEmail -> updateEmail()
            is ProfileContract.Intent.UpdatePhone -> updatePhone()
            is ProfileContract.Intent.UpdateGovernorate -> updateGovernorate()
            is ProfileContract.Intent.UpdateAddress -> updateAddress()
            is ProfileContract.Intent.UpdateImageUrl -> updateImageUrl()
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
            }
            result.onFailure {
                _state.update {
                    it.copy(
                        isLoadingProfile = false,
                        generalError = result.exceptionOrNull()?.message
                    )
                }
            }
        }
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
            }
            result.onFailure {
                _state.update { setFieldValue(it, current.copy(isLoading = false, error = result.exceptionOrNull()?.message)) }
            }
        }
    }

    fun startEditing(
        field: (ProfileContract.State) -> ProfileContract.ProfileFieldState,
        setFieldValue: (ProfileContract.State, ProfileContract.ProfileFieldState) -> ProfileContract.State
    ) {
        _state.update { setFieldValue(it, field(it).copy(isEditing = true, success = false, error = null)) }
    }

    fun cancelEditing(
        field: (ProfileContract.State) -> ProfileContract.ProfileFieldState,
        setFieldValue: (ProfileContract.State, ProfileContract.ProfileFieldState) -> ProfileContract.State
    ) {
        _state.update { setFieldValue(it, field(it).copy(isEditing = false, success = false, error = null)) }
    }

    fun changeValue(
        newValue: String,
        field: (ProfileContract.State) -> ProfileContract.ProfileFieldState,
        setField: (ProfileContract.State, ProfileContract.ProfileFieldState) -> ProfileContract.State
    ) {
        _state.update { setField(it, field(it).copy(value = newValue)) }
    }

    fun updateName() {
        val name = _state.value.name
        updateField(name, { updateUserProfileUseCase.updateName(name.value) }) { state, field ->
            state.copy(name = field)
        }
    }

    fun updateEmail() {
        val email = _state.value.email
        updateField(email, { updateUserEmailUseCase(email.value) }) { state, field ->
            state.copy(email = field)
        }
    }

    fun updatePhone() {
        val phone = _state.value.phone
        updateField(phone, { updateUserProfileUseCase.updatePhone(phone.value) }) { state, field ->
            state.copy(phone = field)
        }
    }

    fun updateGovernorate() {
        val governorate = _state.value.governorate
        updateField(governorate, { updateUserProfileUseCase.updateGovernorate(governorate.value) }) { state, field ->
            state.copy(governorate = field)
        }
    }

    fun updateAddress() {
        val address = _state.value.address
        updateField(address, { updateUserProfileUseCase.updateAddress(address.value) }) { state, field ->
            state.copy(address = field)
        }
    }

    fun updateImageUrl() {
        val imageUrl = _state.value.imageUrl
        updateField(imageUrl, { updateUserProfileUseCase.updateImageUrl(imageUrl.value) }) { state, field ->
            state.copy(imageUrl = field)
        }
    }
}