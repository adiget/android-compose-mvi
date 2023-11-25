package com.example.githubapp.presentation.mapper

import com.example.githubapp.domain.model.User
import com.example.githubapp.presentation.views.views.UserView
import com.example.githubapp.ui.ViewMapper
import javax.inject.Inject

class UserViewMapper @Inject constructor() : ViewMapper<UserView, User> {
    override fun mapToView(type: User): UserView {
        return UserView(
            userName = type.userName,
            profilePic = type.profilePic
        )
    }
}