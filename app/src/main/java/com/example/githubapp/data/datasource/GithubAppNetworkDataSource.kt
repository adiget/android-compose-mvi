package com.example.githubapp.data.datasource

import com.example.githubapp.data.datasource.network.GitRemote
import com.example.githubapp.data.model.GithubUser
import com.example.githubapp.data.model.PullRequestEntity
import com.example.githubapp.data.model.PullRequestGetBody
import com.example.githubapp.data.model.SingleRepoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubAppNetworkDataSource @Inject constructor(
    private val gitRemote: GitRemote
) : GithubAppDataSource {

    override fun getUsers(): Flow<List<GithubUser>> =
        gitRemote.getUsers()

    override fun getUserGitRepositories(username: String): Flow<List<SingleRepoEntity>> {
        return gitRemote.getUserGitRepositories(username)
    }

    override fun getPullRequestList(
        username: String,
        repoName: String,
        state: String
    ): Flow<List<PullRequestEntity>> {
        return gitRemote.getPullRequestList(PullRequestGetBody(username, repoName, state))
    }
}