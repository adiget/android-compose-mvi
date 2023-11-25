package com.example.githubapp.domain

import com.example.githubapp.domain.model.DomainPrData
import javax.inject.Inject

class GetPullRequestsUseCase @Inject constructor(
    private val gitRepository: GithubRepository
){
    operator fun invoke(requestValues: Params) =
        gitRepository.getPullRequestList(
            requestValues.userId,
            requestValues.repoName,
            requestValues.state
            )

    data class Params(
        val userId: String,
        val repoName: String,
        val state: DomainPrData.State
    )
}