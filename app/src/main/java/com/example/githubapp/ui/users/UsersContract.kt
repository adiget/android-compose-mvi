package com.example.githubapp.ui.users

import com.example.githubapp.ui.base.ViewEvent
import com.example.githubapp.ui.base.ViewSideEffect
import com.example.githubapp.ui.base.ViewState
import com.example.githubapp.ui.users.model.UiUserData

class UsersContract {

    sealed class UsersViewEvent : ViewEvent {
        object Refresh : UsersViewEvent()
        object Retry : UsersViewEvent()
        data class UserSelection(val user: UiUserData) : UsersViewEvent()
    }

    sealed interface UsersUiState : ViewState {
        data class Success(val users: List<UiUserData>) : UsersUiState
        object Error : UsersUiState
        object Loading : UsersUiState
    }

    sealed class UsersEffect : ViewSideEffect {
        object DataWasLoaded : UsersEffect()

        sealed class Navigation : UsersEffect() {
            data class ToRepos(val userId: String): Navigation()
        }
    }
}
