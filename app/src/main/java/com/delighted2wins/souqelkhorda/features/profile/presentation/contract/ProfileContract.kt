package com.delighted2wins.souqelkhorda.features.profile.presentation.contract


interface ProfileContract {

    data class ProfileFieldState(
        val value: String = "",
        val isEditing: Boolean = false,
        val isLoading: Boolean = false,
        val error: String? = null,
        val success: Boolean = false
    )

    data class State(
        val name: ProfileFieldState = ProfileFieldState(),
        val email: ProfileFieldState = ProfileFieldState(),
        val phone: ProfileFieldState = ProfileFieldState(),
        val governorate: ProfileFieldState = ProfileFieldState(),
        val address: ProfileFieldState = ProfileFieldState(),
        val imageUrl: ProfileFieldState = ProfileFieldState(),
        val isLoadingProfile: Boolean = false,
        val generalError: String? = null
    )

    sealed class Intent {
        data class LoadProfile(val userId: String) : Intent()
        data class UpdateName(val userId: String, val name: String) : Intent()
        data class UpdateEmail(val userId: String, val email: String) : Intent()
        data class UpdatePhone(val userId: String, val phone: String) : Intent()
        data class UpdateGovernorate(val userId: String, val governorate: String) : Intent()
        data class UpdateAddress(val userId: String, val address: String) : Intent()
        data class UpdateImageUrl(val userId: String, val imageUrl: String) : Intent()
    }

}
