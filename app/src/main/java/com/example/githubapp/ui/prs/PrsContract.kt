package com.example.githubapp.ui.prs

import com.example.githubapp.ui.prs.model.UiPrData
import com.example.githubapp.ui.base.ViewEvent
import com.example.githubapp.ui.base.ViewSideEffect
import com.example.githubapp.ui.base.ViewState

class PrsContract {

    sealed class PrsViewEvent : ViewEvent {
        object Refresh : PrsViewEvent()
        object Retry : PrsViewEvent()
        object BackButtonClicked : PrsViewEvent()
    }

    sealed interface PrsUiState : ViewState{
        data class Success(val prs: List<UiPrData>) : PrsUiState
        object Error : PrsUiState
        object Loading : PrsUiState
    }

    sealed class PrsEffect : ViewSideEffect {
        sealed class Navigation : PrsEffect() {
            object Back : Navigation()
        }
    }
}
