package com.example.githubapp.data.datasource

import com.example.githubapp.data.datasource.network.GitRemote
import com.example.githubapp.data.model.PullRequestEntity
import com.example.githubapp.data.model.PullRequestGetBody
import com.example.githubapp.data.model.SingleRepoEntity
import com.example.githubapp.data.model.UserEntity
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class GithubAppNetworkDataSourceTest {
    private val remote = mockk<GitRemote>()
    private val dataSource = GithubAppNetworkDataSource(remote)

    @Test
    fun when_remote_getUserGitRepositories_returns_valid_repos_dataSource_returns_same_repo_list() = runTest {
        every {
            remote.getUserGitRepositories(USER_NAME)
        } returns flowOf(testRepos)

        val repositories: Flow<List<SingleRepoEntity>> = dataSource.getUserGitRepositories(USER_NAME)

        assertEquals(
            testRepos,
            repositories.first()
        )
    }

    @Test
    fun when_remote_getUserGitRepositories_returns_empty_list_dataSource_returns_empty_list() = runTest {
        every {
            remote.getUserGitRepositories(USER_NAME)
        } returns flowOf(emptyList())

        val repositories: Flow<List<SingleRepoEntity>> = dataSource.getUserGitRepositories(USER_NAME)

        assertEquals(
            emptyList(),
            repositories.first()
        )
    }

    @Test
    fun when_remote_getPullRequestList_returns_valid_prs_dataSource_returns_same_pr_list() = runTest {
        every {
            remote.getPullRequestList(PullRequestGetBody(USER_NAME,REPO_NAME,PR_STATE_ALL))
        } returns flowOf(testPrs)

        val pullRequests: Flow<List<PullRequestEntity>> =
            dataSource.getPullRequestList(USER_NAME, REPO_NAME, PR_STATE_ALL)

        assertEquals(
            testPrs,
            pullRequests.first()
        )
    }

    @Test
    fun when_remote_getPullRequestList_returns_empty_list_dataSource_returns_empty_list() = runTest {
        every {
            remote.getPullRequestList(PullRequestGetBody(USER_NAME,REPO_NAME,PR_STATE_ALL))
        } returns flowOf(emptyList())

        val pullRequests: Flow<List<PullRequestEntity>> =
            dataSource.getPullRequestList(USER_NAME, REPO_NAME, PR_STATE_ALL)

        assertEquals(
            emptyList(),
            pullRequests.first()
        )
    }

    private val testRepos = listOf(
        SingleRepoEntity(
            "repo1",
            "repo1 desc",
            1,
            1,
            "branch1"
        ),
        SingleRepoEntity(
            "repo2",
            "repo2 desc",
            2,
            2,
            "branch2"
        ),
        SingleRepoEntity(
            "repo3",
            "repo3 desc",
            3,
            3,
            "branch3"
        )
    )

    private val testPrs = listOf(
        PullRequestEntity(
            1,
            "pr1 desc",
            "pr1 title",
            UserEntity("user1"),
            ""
        ),
        PullRequestEntity(
            2,
            "pr2 desc",
            "pr2 title",
            UserEntity("user2"),
            ""
        ),
        PullRequestEntity(
            3,
            "pr3 desc",
            "pr3 title",
            UserEntity("user3"),
            ""
        )
    )

    private companion object {
        const val USER_NAME = "userName"
        const val REPO_NAME = "repoName"
        const val PR_STATE_ALL = "ALL"
    }
}