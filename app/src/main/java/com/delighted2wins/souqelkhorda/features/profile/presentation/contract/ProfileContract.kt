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

        val isLoadingOrders: Boolean = false,
        val completedCount: Int = 0,
        val pendingCount: Int = 0,
        val cancelledCount: Int = 0,

        val generalError: String? = null
    )

    sealed class Intent {
        data object LoadProfile : Intent()
        object LoadOrders : Intent()

        data class ChangeName(val name: String) : Intent()
        data class ChangeEmail(val email: String) : Intent()
        data class ChangePhone(val phone: String) : Intent()
        data class ChangeGovernorate(val governorate: String) : Intent()
        data class ChangeAddress(val address: String) : Intent()
        data class ChangeImageUrl(val imageUrl: String) : Intent()

        data object SaveName : Intent()
        data object SaveEmail : Intent()
        data object SavePhone : Intent()
        data object SaveGovernorate : Intent()
        data object SaveAddress : Intent()
        data object SaveImageUrl : Intent()

        data class StartEditing(
            val fieldSelector: (State) -> ProfileFieldState,
            val fieldSetter: (State, ProfileFieldState) -> State
        ) : Intent()

        data class CancelEditing(
            val fieldSelector: (State) -> ProfileFieldState,
            val fieldSetter: (State, ProfileFieldState) -> State
        ) : Intent()

        data class ChangeLanguage(val lang: String) : Intent()

        data object Logout : Intent()
        data object NavigateToHistory : Intent()
    }

    sealed class Effect {
        object NavigateToHistory : Effect()
        object Logout : Effect()
    }
}

