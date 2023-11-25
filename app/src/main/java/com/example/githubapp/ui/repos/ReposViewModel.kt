package com.example.githubapp.ui.repos

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.githubapp.domain.GetUserReposUseCase
import com.example.githubapp.ui.repos.mapper.RepoViewMapper
import com.example.githubapp.common.Result
import com.example.githubapp.common.asResult
import com.example.githubapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject internal constructor(
    val viewMapper: RepoViewMapper,
    val useCase: GetUserReposUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ReposContract.ReposViewEvent, ReposContract.ReposUiState, ReposContract.ReposEffect>() {

    val userId = savedStateHandle[USER_ID_SAVED_STATE_KEY] ?: NO_USER_ID
    init {
        getReposForUser()
    }
    override fun setInitialState() = ReposContract.ReposUiState.Loading

    override fun handleEvents(event: ReposContract.ReposViewEvent) {
        when (event) {
            is ReposContract.ReposViewEvent.BackButtonClicked -> setEffect {
                ReposContract.ReposEffect.Navigation.Back
            }
            is ReposContract.ReposViewEvent.Refresh,
            is ReposContract.ReposViewEvent.Retry -> getReposForUser()
        }
    }

    private fun getReposForUser( ) {
        viewModelScope.launch {
            useCase(GetUserReposUseCase.Params(userId))
                .asResult()
                .collect { result ->
                    when (result) {
                        is Result.Loading -> setState { ReposContract.ReposUiState.Loading }
                        is Result.Success -> setState {
                            ReposContract.ReposUiState.Success(
                                result.data.map { viewMapper.mapToView(it) }
                            )
                        }
                        is Result.Error -> setState { ReposContract.ReposUiState.Error }
                    }
                }
        }
    }

    companion object {
        private const val NO_USER_ID = ""
        const val USER_ID_SAVED_STATE_KEY = "userId"
    }
}
