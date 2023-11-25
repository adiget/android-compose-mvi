package com.example.githubapp.ui.users.compose

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.githubapp.R
import com.example.githubapp.ui.base.SIDE_EFFECTS_KEY
import com.example.githubapp.ui.common.NetworkError
import com.example.githubapp.ui.common.Progress
import com.example.githubapp.ui.users.UsersContract
import com.example.githubapp.ui.users.UsersViewModel
import com.example.githubapp.ui.users.model.buildUiUserDataPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UsersScreen(
    state: UsersContract.UsersUiState,
    effectFlow: Flow<UsersContract.UsersEffect>?,
    onEventSent: (event: UsersContract.UsersViewEvent) -> Unit,
    onNavigationRequested: (navigationEffect: UsersContract.UsersEffect.Navigation) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val snackBarMessage = stringResource(R.string.users_screen_snackbar_loaded_message)

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is UsersContract.UsersEffect.DataWasLoaded -> {
                    snackbarHostState.showSnackbar(
                        message = snackBarMessage,
                        duration = SnackbarDuration.Short
                    )
                }
                is UsersContract.UsersEffect.Navigation.ToRepos -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = { UsersTopBar() },
        content = { padding ->
            when(state){
                UsersContract.UsersUiState.Loading -> Progress()

                UsersContract.UsersUiState.Error -> NetworkError {
                    onEventSent(UsersContract.UsersViewEvent.Retry)
                }

                is UsersContract.UsersUiState.Success ->
                    UsersList(
                        users = state.users,
                        contentPadding = padding,
                        onRefresh = { onEventSent(UsersContract.UsersViewEvent.Refresh) },
                        onItemClick = { onEventSent(UsersContract.UsersViewEvent.UserSelection(it)) }
                    )
            }
        }
    )
}

@Composable
fun UsersScreen(
    viewModel: UsersViewModel = hiltViewModel(),
    onNavigationRequested: (navigationEffect: UsersContract.UsersEffect.Navigation) -> Unit
) {
    val state: UsersContract.UsersUiState by viewModel.viewState.collectAsStateWithLifecycle()
    val effectFlow: Flow<UsersContract.UsersEffect> = viewModel.effect
    val snackbarHostState = remember { SnackbarHostState() }
    val snackBarMessage = stringResource(R.string.users_screen_snackbar_loaded_message)

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow.onEach { effect ->
            when (effect) {
                is UsersContract.UsersEffect.DataWasLoaded -> {
                    snackbarHostState.showSnackbar(
                        message = snackBarMessage,
                        duration = SnackbarDuration.Short
                    )
                }

                is UsersContract.UsersEffect.Navigation.ToRepos -> onNavigationRequested(effect)
            }
        }.collect()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = { UsersTopBar() },
        content = { padding ->
            when(state){
                UsersContract.UsersUiState.Loading -> Progress()

                UsersContract.UsersUiState.Error -> NetworkError {
                    viewModel.setEvent(UsersContract.UsersViewEvent.Retry)
                }

                is UsersContract.UsersUiState.Success ->
                    UsersList(
                        users = (state as UsersContract.UsersUiState.Success).users,
                        contentPadding = padding,
                        onRefresh = { viewModel.setEvent(UsersContract.UsersViewEvent.Refresh) },
                        onItemClick = { viewModel.setEvent(UsersContract.UsersViewEvent.UserSelection(it)) }
                    )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun UsersScreenSuccessPreview() {
    val users = List(3) { buildUiUserDataPreview() }
    UsersScreen(
        state = UsersContract.UsersUiState.Success(users = users),
        effectFlow = null,
        onEventSent = {},
        onNavigationRequested = {},
    )
}

@Preview(showBackground = true)
@Composable
fun UsersScreenErrorPreview() {
    UsersScreen(
        state = UsersContract.UsersUiState.Error,
        effectFlow = null,
        onEventSent = {},
        onNavigationRequested = {},
    )
}
