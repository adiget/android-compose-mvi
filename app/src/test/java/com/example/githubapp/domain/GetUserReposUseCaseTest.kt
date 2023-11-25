package com.example.githubapp.domain

import com.example.githubapp.domain.model.DomainRepoData
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class GetUserReposUseCaseTest {
    private val repository = mockk<GithubRepository>()
    val useCase = GetUserReposUseCase(repository)

    @Test
    fun when_repository_getUserGitRepositories_returns_valid_repolist_useCase_returns_same_list() = runTest {
        every { repository.getUserGitRepositories(USER_ID) } returns flowOf(testRepos)

        val repositories: Flow<List<DomainRepoData>> = useCase(GetUserReposUseCase.Params(USER_ID))

        assertEquals(
            testRepos,
            repositories.first()
        )
    }

    @Test
    fun when_repository_getUserGitRepositories_returns_empty_list_useCase_returns_empty_list() = runTest {
        every { repository.getUserGitRepositories(USER_ID) } returns flowOf(emptyList())

        val repositories: Flow<List<DomainRepoData>> = useCase(GetUserReposUseCase.Params(USER_ID))

        assertEquals(
            emptyList(),
            repositories.first()
        )
    }

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

    private companion object {
        const val USER_ID = "userId"
    }
}