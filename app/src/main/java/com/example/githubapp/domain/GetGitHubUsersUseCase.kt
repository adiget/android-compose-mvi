package com.example.githubapp.domain

import com.example.githubapp.domain.model.DomainUserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGitHubUsersUseCase @Inject constructor(
    private val gitRepository: GithubRepository
) {
    operator fun invoke(): Flow<List<DomainUserData>> =
        gitRepository.getGitHubUsers()
}