package com.example.githubapp.ui.repos

import com.example.githubapp.ui.repos.model.UiRepoData
import com.example.githubapp.ui.base.ViewEvent
import com.example.githubapp.ui.base.ViewSideEffect
import com.example.githubapp.ui.base.ViewState

class ReposContract {

    sealed class ReposViewEvent : ViewEvent {
        object Refresh : ReposViewEvent()
        object Retry : ReposViewEvent()
        object BackButtonClicked : ReposViewEvent()
    }

    sealed interface ReposUiState : ViewState{
        data class Success(val repos: List<UiRepoData>) : ReposUiState
        object Error : ReposUiState
        object Loading : ReposUiState
    }

    sealed class ReposEffect : ViewSideEffect {
        sealed class Navigation : ReposEffect() {
            object Back : Navigation()
        }
    }
}
