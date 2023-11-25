package com.example.githubapp.ui.prs

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.githubapp.domain.GetPullRequestsUseCase
import com.example.githubapp.domain.model.DomainPrData
import com.example.githubapp.domain.model.User
import com.example.githubapp.presentation.mapper.UserViewMapper
import com.example.githubapp.presentation.views.views.UserView
import com.example.githubapp.ui.prs.PrsContract
import com.example.githubapp.ui.prs.PrsViewModel
import com.example.githubapp.ui.prs.mapper.PrViewMapper
import com.example.githubapp.ui.prs.model.UiPrData
import com.example.githubapp.ui.MainDispatcherRule
import com.example.githubapp.ui.TestUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class PrsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val useCase = mockk<GetPullRequestsUseCase>()
    private val viewMapper = PrViewMapper(UserViewMapper())
    private val savedStateHandle = SavedStateHandle(
        mapOf(
            PrsViewModel.USER_ID_SAVED_STATE_KEY to USER_ID,
            PrsViewModel.REPO_NAME_SAVED_STATE_KEY to REPO_NAME,
            PrsViewModel.PULL_REQUEST_STATE_STATE_KEY to REPO_STATE
        )
    )
    private lateinit var viewModel: PrsViewModel

    @Test
    fun userId_repoName_prState_matchesFromSavedStateHandle() {
        coEvery {
            useCase(
                GetPullRequestsUseCase.Params(
                    userId = USER_ID,
                    repoName = REPO_NAME,
                    state = DomainPrData.State.valueOf(REPO_STATE)
                )
            )
        } returns flowOf(testPrs)

        viewModel = PrsViewModel(
            viewMapper = viewMapper,
            useCase = useCase,
            savedStateHandle = savedStateHandle
        )

        assertEquals(USER_ID, viewModel.userId)
        assertEquals(REPO_NAME, viewModel.repoName)
        assertEquals(REPO_STATE, viewModel.prState)
    }

    @Test
    fun `when initialized usecase emits loading and data`() = runTest {
        val testUseCase = TestUseCase<List<DomainPrData>>()

        every {
            useCase(
                GetPullRequestsUseCase.Params(
                    userId = USER_ID,
                    repoName = REPO_NAME,
                    state = DomainPrData.State.valueOf(REPO_STATE)
                )
            )
        } returns testUseCase.invoke()

        viewModel = PrsViewModel(
            viewMapper = viewMapper,
            useCase = useCase,
            savedStateHandle = savedStateHandle
        )

        viewModel.viewState.test {
            val firstItem = awaitItem()
            assertEquals(PrsContract.PrsUiState.Loading, firstItem)

            testUseCase.sendUsers(testPrs)

            val secondItem = awaitItem()
            assertEquals(PrsContract.PrsUiState.Success(testPrsForView), secondItem)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when usecase emit empty list viewmodel returns ui state with empty list`() = runTest {
        every {
            useCase(
                GetPullRequestsUseCase.Params(
                    userId = USER_ID,
                    repoName = REPO_NAME,
                    state = DomainPrData.State.valueOf(REPO_STATE)
                )
            )
        } returns flowOf(emptyList())

        viewModel = PrsViewModel(
            viewMapper = viewMapper,
            useCase = useCase,
            savedStateHandle = savedStateHandle
        )

        viewModel.viewState.test {
            assertEquals(PrsContract.PrsUiState.Success(emptyList()), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when usecase emit error viewmodel returns error ui state`() = runTest {
        every {
            useCase(
                GetPullRequestsUseCase.Params(
                    userId = USER_ID,
                    repoName = REPO_NAME,
                    state = DomainPrData.State.valueOf(REPO_STATE)
                )
            )
        } returns flow {
            throw Exception("Error network call")
        }

        viewModel = PrsViewModel(
            viewMapper = viewMapper,
            useCase = useCase,
            savedStateHandle = savedStateHandle
        )

        viewModel.viewState.test {
            assertEquals(PrsContract.PrsUiState.Error, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}

private const val USER_ID = "user1"
private const val REPO_NAME = "repo1"
private val REPO_STATE = "ALL"

private val testPrs = listOf(
    DomainPrData(
        1,
        "pr1 desc",
        "pr1 title",
        User("user1"),
        "2022-10-25T16:56:16Z",
        "2022-10-25T16:56:16Z"
    ),
    DomainPrData(
        2,
        "pr2 desc",
        "pr2 title",
        User("user2"),
        "2022-10-25T16:56:16Z",
        "2022-10-25T16:56:16Z"
    ),
    DomainPrData(
        3,
        "pr3 desc",
        "pr3 title",
        User("user3"),
        "2022-10-25T16:56:16Z",
        "2022-10-25T16:56:16Z"
    )
)

private val testPrsForView = listOf(
    UiPrData(
        1,
        "pr1 title",
        "pr1 desc",
        UserView("user1"),
        "25 Oct'22",
        "25 Oct'22"
    ),
    UiPrData(
        2,
        "pr2 title",
        "pr2 desc",
        UserView("user2"),
        "25 Oct'22",
        "25 Oct'22"
    ),
    UiPrData(
        3,
        "pr3 title",
        "pr3 desc",
        UserView("user3"),
        "25 Oct'22",
        "25 Oct'22"
    )
)