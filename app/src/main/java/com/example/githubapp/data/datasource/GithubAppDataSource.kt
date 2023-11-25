package com.example.githubapp.data.datasource

import com.example.githubapp.data.model.GithubUser
import com.example.githubapp.data.model.PullRequestEntity
import com.example.githubapp.data.model.SingleRepoEntity
import kotlinx.coroutines.flow.Flow

interface GithubAppDataSource {
    fun getUsers(): Flow<List<GithubUser>>

    fun getUserGitRepositories(username: String): Flow<List<SingleRepoEntity>>

    fun getPullRequestList(
        username: String,
        repoName: String,
        state: String
    ): Flow<List<PullRequestEntity>>
}