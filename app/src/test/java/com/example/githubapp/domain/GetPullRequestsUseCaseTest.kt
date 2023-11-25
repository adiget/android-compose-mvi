package com.example.githubapp.domain

import com.example.githubapp.domain.model.DomainPrData
import com.example.githubapp.domain.model.User
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class GetPullRequestsUseCaseTest {
    private val repository = mockk<GithubRepository>()
    val useCase = GetPullRequestsUseCase(repository)

    @Test
    fun when_repository_getPullRequestList_returns_valid_prs_useCase_returns_same_pr_list() = runTest {
        every {
            repository.getPullRequestList(USER_ID, REPO_NAME, PR_STATE_ALL)
        } returns flowOf(testPrs)

        val pullRequests: Flow<List<DomainPrData>> = useCase(
            GetPullRequestsUseCase.Params(USER_ID, REPO_NAME, PR_STATE_ALL)
        )

        assertEquals(
            testPrs,
            pullRequests.first()
        )
    }

    @Test
    fun when_repository_getPullRequestList_returns_empty_list_useCase_returns_empty_list() = runTest {
        every {
            repository.getPullRequestList(USER_ID, REPO_NAME, PR_STATE_ALL)
        } returns flowOf(emptyList())

        val pullRequests: Flow<List<DomainPrData>> = useCase(
            GetPullRequestsUseCase.Params(USER_ID, REPO_NAME, PR_STATE_ALL)
        )

        assertEquals(
            emptyList(),
            pullRequests.first()
        )
    }

    private val testPrs = listOf(
        DomainPrData(
            1,
            "pr1 desc",
            "pr1 title",
            User("user1"),
            ""
        ),
        DomainPrData(
            2,
            "pr2 desc",
            "pr2 title",
            User("user2"),
            ""
        ),
        DomainPrData(
            3,
            "pr3 desc",
            "pr3 title",
            User("user3"),
            ""
        )
    )

    private companion object {
        const val USER_ID = "userId"
        const val REPO_NAME = "repoName"
        val PR_STATE_ALL = DomainPrData.State.ALL
    }
}