package com.example.githubapp.ui.repos.mapper

import com.example.githubapp.domain.model.DomainRepoData
import com.example.githubapp.ui.ViewMapper
import com.example.githubapp.ui.repos.model.UiRepoData
import javax.inject.Inject

class RepoViewMapper @Inject constructor() :
    ViewMapper<UiRepoData, DomainRepoData> {

    override fun mapToView(type: DomainRepoData) =
        UiRepoData(
            repoName = type.repoName,
            repoDescription = type.repoDescription,
            openIssuesCount = type.openIssuesCount,
            forksCount = type.forksCount,
            defaultBranch = type.defaultBranch
        )
    }