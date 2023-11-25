package com.example.githubapp.ui.users

import androidx.lifecycle.viewModelScope
import com.example.githubapp.common.Result
import com.example.githubapp.common.asResult
import com.example.githubapp.domain.GetGitHubUsersUseCase
import com.example.githubapp.ui.base.BaseViewModel
import com.example.githubapp.ui.users.mapper.UserViewMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject internal constructor(
    val viewMapper: UserViewMapper,
    val useCase: GetGitHubUsersUseCase
) : BaseViewModel<UsersContract.UsersViewEvent, UsersContract.UsersUiState, UsersContract.UsersEffect>() {

    init {
        getUsers()
        //usersUiState()
    }

    override fun setInitialState() = UsersContract.UsersUiState.Loading
//    override fun reduceState(
//        currentState: UsersContract.UsersUiState,
//        event: ViewEvent
//    ): UsersContract.UsersUiState {
//        return when (event) {
//            is UsersContract.UsersViewEvent.Refresh,
//            is UsersContract.UsersViewEvent.Retry -> getUsers()
//            is UsersContract.UsersViewEvent.UserSelection -> setEffect { UsersContract.Effect.Navigation.ToRepos(event.user.userId) }
//            else -> { getUsers() }
//        }
//    }

    override fun handleEvents(event: UsersContract.UsersViewEvent) {
        when (event) {
            is UsersContract.UsersViewEvent.UserSelection -> setEffect {
                UsersContract.UsersEffect.Navigation.ToRepos(event.user.userId)
            }
            is UsersContract.UsersViewEvent.Refresh,
            is UsersContract.UsersViewEvent.Retry -> getUsers()
        }
    }

//    override fun reduceState(
//        currentState: UsersContract.UsersUiState,
//        event: UsersContract.UsersViewEvent
//    ): UsersContract.UsersUiState = when (event) {
//        is ScreenStarted,
//        is RefreshClicked -> {
//            refreshMovies()
//            currentState.copy(isLoading = true)
//        }
//
//        is MovieClicked -> {
//            val clickedId = event.movie.id
//            val updatedMovies = currentState.movies.toggleSelection(clickedId)
//            currentState.copy(movies = updatedMovies)
//        }
//
//        is ShuffleClicked -> {
//            val shuffledMovies = currentState.movies.shuffled()
//            currentState.copy(movies = shuffledMovies)
//        }
//
//        is MoviesLoaded -> {
//            val newMovies = event.movies
//            val currentMovies = currentState.movies
//            val updatedMovies = newMovies.copySelectionFrom(currentMovies)
//            currentState.copy(isLoading = false, movies = updatedMovies)
//        }
//    }
//
//    private fun refreshMovies() = viewModelScope.launch {
//        val movies = fetchMovies()
//        state.handleEvent(MoviesLoaded(movies))
//    }

//    fun getUsers() {
//        viewModelScope.launch {
//            usersUiStateAsFlow().collect {
//                return@collect
//            }
//        }
//        //return usersUiState()
////        setState {
////            usersUiState()
////                .stateIn(
////                    scope = viewModelScope,
////                    started = SharingStarted.WhileSubscribed(5_000),
////                    initialValue = UsersContract.UsersUiState.Loading
////                )
////        }
//
////        viewModelScope.launch {
////            setState { copy(isLoading = true, isError = false) }
////
////            useCase()
////                .onSuccess { users ->
////                    setState { copy(users = users, isLoading = false) }
////                    setEffect { UsersContract.Effect.DataWasLoaded }
////                }
////                .onFailure {
////                    setState { copy(isError = true, isLoading = false) }
////                }
////        }
//    }

    private fun getUsers() {
        viewModelScope.launch {
            useCase()
                .asResult()
                .collect { result ->
                    when (result) {
                        is Result.Loading -> setState { UsersContract.UsersUiState.Loading }
                        is Result.Success -> setState {
                            UsersContract.UsersUiState.Success(
                                result.data.map { viewMapper.mapToView(it) }
                            )
                        }
                        is Result.Error -> setState { UsersContract.UsersUiState.Error }
                    }
                }
        }
    }
}
