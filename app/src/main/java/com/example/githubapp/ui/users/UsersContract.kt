package com.example.githubapp.ui.users

import com.example.githubapp.ui.users.model.UiUserData
import com.example.githubapp.ui.base.ViewEvent
import com.example.githubapp.ui.base.ViewSideEffect
import com.example.githubapp.ui.base.ViewState

class UsersContract {

    sealed class UsersViewEvent : ViewEvent {
        object Refresh : UsersViewEvent()
        object Retry : UsersViewEvent()
        data class UserSelection(val user: UiUserData) : UsersViewEvent()


        //object ScreenStarted : UsersViewEvent()
    //        object RefreshClicked : UsersViewEvent()
//        object ShuffleClicked : UsersViewEvent()
//        data class MoviesLoaded(val movies: List<Movie>) : UsersViewEvent()
//        data class MovieClicked(val movie: Movie) : UsersViewEvent()
    }

//    data class State(
//        val users: List<UiUserData>,
//        val isLoading: Boolean,
//        val isError: Boolean,
//    ) : ViewState

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
