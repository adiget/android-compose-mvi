package com.example.githubapp.ui.prs

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.githubapp.common.Result
import com.example.githubapp.common.asResult
import com.example.githubapp.domain.GetPullRequestsUseCase
import com.example.githubapp.domain.model.DomainPrData
import com.example.githubapp.ui.base.BaseViewModel
import com.example.githubapp.ui.prs.mapper.PrViewMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PrsViewModel @Inject internal constructor(
    val viewMapper: PrViewMapper,
    val useCase: GetPullRequestsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<PrsContract.PrsViewEvent, PrsContract.PrsUiState, PrsContract.PrsEffect>() {

    val userId = savedStateHandle[USER_ID_SAVED_STATE_KEY] ?: NO_USER_ID
    val repoName = savedStateHandle[REPO_NAME_SAVED_STATE_KEY] ?: NO_REPO_NAME
    val prState = savedStateHandle[PULL_REQUEST_STATE_STATE_KEY] ?: NO_STATE

    override val viewState: StateFlow<PrsContract.PrsUiState> = getPullRequests()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PrsContract.PrsUiState.Loading,
        )

    override fun setInitialState() = PrsContract.PrsUiState.Loading

    override fun handleEvents(event: PrsContract.PrsViewEvent) {
        when (event) {
            is PrsContract.PrsViewEvent.BackButtonClicked -> setEffect {
                PrsContract.PrsEffect.Navigation.Back
            }
            is PrsContract.PrsViewEvent.Refresh,
            is PrsContract.PrsViewEvent.Retry -> getPullRequests()
        }
    }

    private fun getPullRequests(): Flow<PrsContract.PrsUiState> =
        useCase(
            GetPullRequestsUseCase.Params(
                userId,
                repoName,
                DomainPrData.State.valueOf(prState)
            )
        )
        .asResult()
        .map { result ->
            when (result) {
                is Result.Loading -> PrsContract.PrsUiState.Loading
                is Result.Success -> PrsContract.PrsUiState.Success(
                        result.data.map {
                            viewMapper.mapToView(it)
                        }
                    )
                is Result.Error -> PrsContract.PrsUiState.Error
            }
        }

    companion object {
        private const val NO_USER_ID = ""
        private const val NO_REPO_NAME = ""
        private const val NO_STATE = ""
        const val USER_ID_SAVED_STATE_KEY = "userId"
        const val REPO_NAME_SAVED_STATE_KEY = "repoName"
        const val PULL_REQUEST_STATE_STATE_KEY = "prState"
    }
}


