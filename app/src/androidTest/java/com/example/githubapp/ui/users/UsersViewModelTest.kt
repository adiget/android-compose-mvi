package com.example.githubapp.ui.users

import app.cash.turbine.test
import com.example.githubapp.domain.GetGitHubUsersUseCase
import com.example.githubapp.domain.model.DomainUserData
import com.example.githubapp.ui.users.UsersContract
import com.example.githubapp.ui.users.UsersViewModel
import com.example.githubapp.ui.users.mapper.UserViewMapper
import com.example.githubapp.ui.users.model.UiUserData
import com.example.githubapp.ui.MainDispatcherRule
import com.example.githubapp.ui.TestUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class UsersViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val viewMapper = UserViewMapper()
    private val useCase = mockk<GetGitHubUsersUseCase>()
    private lateinit var viewModel: UsersViewModel

    @Test
    fun `when initialized usecase emits loading and data`() = runTest {
        val testUseCase = TestUseCase<List<DomainUserData>>()

        every { useCase() } returns testUseCase.invoke()

        viewModel = UsersViewModel(viewMapper = viewMapper, useCase = useCase)

        viewModel.viewState.test {
            val firstItem = awaitItem()
            assertEquals(UsersContract.UsersUiState.Loading, firstItem)

            testUseCase.sendUsers(testUsers)

            val secondItem = awaitItem()
            assertEquals(UsersContract.UsersUiState.Success(testUsersForView), secondItem)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when usecase emit empty list viewmodel returns ui state with empty list`() = runTest {
        every { useCase() } returns flowOf(emptyList())

        viewModel = UsersViewModel(viewMapper = viewMapper, useCase = useCase)

        viewModel.viewState.test {
            assertEquals(UsersContract.UsersUiState.Success(emptyList()), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when usecase emit error viewmodel returns error ui state`() = runTest {
        every { useCase() } returns flow {
            throw Exception("Error network call")
        }

        viewModel = UsersViewModel(viewMapper = viewMapper, useCase = useCase)

        viewModel.viewState.test {
            assertEquals(UsersContract.UsersUiState.Error, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}

private val testUsers = listOf(
    DomainUserData(
        userId = "1",
        avatarUrl = "http://test1AvatarUrl",
        htmlUrl = "http://test1HtmlUrl"
    ),
    DomainUserData(
        userId = "2",
        avatarUrl = "http://test2AvatarUrl",
        htmlUrl = "http://test2HtmlUrl"
    ),
    DomainUserData(
        userId = "3",
        avatarUrl = "http://test3AvatarUrl",
        htmlUrl = "http://test3HtmlUrl"
    )
)

private val testUsersForView = listOf(
    UiUserData(
        userId = "1",
        avatarUrl = "http://test1AvatarUrl",
        htmlUrl = "http://test1HtmlUrl"
    ),
    UiUserData(
        userId = "2",
        avatarUrl = "http://test2AvatarUrl",
        htmlUrl = "http://test2HtmlUrl"
    ),
    UiUserData(
        userId = "3",
        avatarUrl = "http://test3AvatarUrl",
        htmlUrl = "http://test3HtmlUrl"
    )
)