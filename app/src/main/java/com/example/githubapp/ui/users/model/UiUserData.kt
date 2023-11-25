package com.example.githubapp.ui.users.model

data class UiUserData(
    val userId: String = "",
    val avatarUrl: String = "",
    val htmlUrl: String = "",
)

fun buildUiUserDataPreview() = UiUserData(
    userId = "mojombo",
    avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
    htmlUrl = "https://github.com/mojombo",
)
