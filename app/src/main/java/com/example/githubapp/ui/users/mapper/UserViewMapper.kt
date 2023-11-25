package com.example.githubapp.ui.users.mapper

import com.example.githubapp.domain.model.DomainUserData
import com.example.githubapp.ui.ViewMapper
import com.example.githubapp.ui.users.model.UiUserData
import javax.inject.Inject

class UserViewMapper @Inject constructor() :
    ViewMapper<UiUserData, DomainUserData> {

    override fun mapToView(type: DomainUserData) =
        UiUserData(
            userId = type.userId,
            avatarUrl = type.avatarUrl,
            htmlUrl = type.htmlUrl
        )
    }