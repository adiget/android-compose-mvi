package com.example.githubapp.domain

import com.example.githubapp.domain.model.DomainPrData
import com.example.githubapp.domain.model.DomainRepoData
import com.example.githubapp.domain.model.DomainUserData
import kotlinx.coroutines.flow.Flow

interface GithubRepository {

    fun getGitHubUsers(): Flow<List<DomainUserData>>

    fun getUserGitRepositories(username: String): Flow<List<DomainRepoData>>

    fun getPullRequestList(
        username: String,
        repoName: String,
        state: DomainPrData.State = DomainPrData.State.ALL
    ): Flow<List<DomainPrData>>
}