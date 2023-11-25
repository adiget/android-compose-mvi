package com.example.githubapp.data.mapper

import com.example.githubapp.data.model.SingleRepoEntity
import com.example.githubapp.domain.model.DomainRepoData
import javax.inject.Inject

class RepoDataMapper @Inject constructor() : DataMapper<SingleRepoEntity, DomainRepoData> {
    override fun mapFromEntity(type: SingleRepoEntity): DomainRepoData {
        return DomainRepoData(
            repoName = type.repoName,
            defaultBranch = type.defaultBranch,
            forksCount = type.forksCount,
            openIssuesCount = type.openIssuesCount,
            repoDescription = type.repoDescription
        )
    }
}