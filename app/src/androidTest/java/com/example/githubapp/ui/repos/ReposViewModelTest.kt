package com.example.githubapp.ui.repos

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.githubapp.domain.GetUserReposUseCase
import com.example.githubapp.domain.model.DomainRepoData
import com.example.githubapp.ui.repos.ReposContract
import com.example.githubapp.ui.repos.ReposViewModel
import com.example.githubapp.ui.repos.mapper.RepoViewMapper
import com.example.githubapp.ui.repos.model.UiRepoData
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

class ReposViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val useCase = mockk<GetUserReposUseCase>()
    private val viewMapper = RepoViewMapper()
    private lateinit var viewModel: ReposViewModel

    @Test
    fun userId_matchesUserNameFromSavedStateHandle() {
        coEvery { useCase(GetUserReposUseCase.Params(userId = USER_ID)) } returns flowOf(
            testRepos
        )

        viewModel = ReposViewModel(
            viewMapper = RepoViewMapper(),
            useCase = useCase,
            savedStateHandle = SavedStateHandle(mapOf(ReposViewModel.USER_ID_SAVED_STATE_KEY to USER_ID))
        )

        assertEquals(USER_ID, viewModel.userId)
    }

    @Test
    fun `when initialized usecase emits loading and data`() = runTest {
        val testUseCase = TestUseCase<List<DomainRepoData>>()

        every { useCase(GetUserReposUseCase.Params(userId = USER_ID)) } returns testUseCase.invoke()

        viewModel = ReposViewModel(
            viewMapper = viewMapper,
            useCase = useCase,
            savedStateHandle = SavedStateHandle(mapOf(ReposViewModel.USER_ID_SAVED_STATE_KEY to USER_ID))
        )

        viewModel.viewState.test {
            val firstItem = awaitItem()
            assertEquals(ReposContract.ReposUiState.Loading, firstItem)

            testUseCase.sendUsers(testRepos)

            val secondItem = awaitItem()
            assertEquals(ReposContract.ReposUiState.Success(testReposForView), secondItem)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when usecase emit empty list viewmodel returns ui state with empty list`() = runTest {
        every { useCase(GetUserReposUseCase.Params(userId = USER_ID)) } returns flowOf(emptyList())

        viewModel = ReposViewModel(
            viewMapper = viewMapper,
            useCase = useCase,
            savedStateHandle = SavedStateHandle(mapOf(ReposViewModel.USER_ID_SAVED_STATE_KEY to USER_ID))
        )

        viewModel.viewState.test {
            assertEquals(ReposContract.ReposUiState.Success(emptyList()), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when usecase emit error viewmodel returns error ui state`() = runTest {
        every { useCase(GetUserReposUseCase.Params(userId = USER_ID)) } returns flow {
            throw Exception("Error network call")
        }

        viewModel = ReposViewModel(
            viewMapper = viewMapper,
            useCase = useCase,
            savedStateHandle = SavedStateHandle(mapOf(ReposViewModel.USER_ID_SAVED_STATE_KEY to USER_ID))
        )

        viewModel.viewState.test {
            assertEquals(ReposContract.ReposUiState.Error, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}

private const val USER_ID = "user_id"

private val testRepos = listOf(
    DomainRepoData(
        "repo1",
        "repo1 desc",
        1,
        1,
        "branch1"
    ),
    DomainRepoData(
        "repo2",
        "repo2 desc",
        2,
        2,
        "branch2"
    ),
    DomainRepoData(
        "repo3",
        "repo3 desc",
        3,
        3,
        "branch3"
    )
)

private val testReposForView = listOf(
    UiRepoData(
        repoName = "repo1",
        repoDescription = "repo1 desc",
        openIssuesCount = 1,
        forksCount = 1,
        defaultBranch = "branch1"
    ),
    UiRepoData(
        repoName = "repo2",
        repoDescription = "repo2 desc",
        openIssuesCount = 2,
        forksCount = 2,
        defaultBranch = "branch2"
    ),
    UiRepoData(
        repoName = "repo3",
        repoDescription = "repo3 desc",
        openIssuesCount = 3,
        forksCount = 3,
        defaultBranch = "branch3"
    )
)