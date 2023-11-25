package com.example.githubapp.data.datasource.network

import com.example.githubapp.data.model.GithubUser
import com.example.githubapp.data.model.PullRequestEntity
import com.example.githubapp.data.model.PullRequestGetBody
import com.example.githubapp.data.model.SingleRepoEntity
import kotlinx.coroutines.flow.Flow

interface GitRemote {
    fun getUsers(): Flow<List<GithubUser>>

    fun getUserGitRepositories(username: String): Flow<List<SingleRepoEntity>>

    fun getPullRequestList(pullRequestGetBody: PullRequestGetBody): Flow<List<PullRequestEntity>>
}