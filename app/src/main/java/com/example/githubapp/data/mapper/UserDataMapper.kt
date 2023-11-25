package com.example.githubapp.data.mapper

import com.example.githubapp.data.model.GithubUser
import com.example.githubapp.domain.model.DomainUserData
import javax.inject.Inject

class UserDataMapper @Inject constructor() : DataMapper<GithubUser, DomainUserData> {

    override fun mapFromEntity(type: GithubUser): DomainUserData =
        DomainUserData(
            userId = type.login,
            avatarUrl = type.avatarUrl,
            htmlUrl = type.htmlUrl
        )
}