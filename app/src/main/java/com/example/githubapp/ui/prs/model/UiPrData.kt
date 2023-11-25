package com.example.githubapp.ui.prs.model

import com.example.githubapp.presentation.views.views.UserView

data class UiPrData(
    var id : Int = -1,
    var prTitle: String = "",
    var prDesc: String = "",
    var user: UserView = UserView(),
    var closedAt: String = "",
    var createdAt: String = ""
)

fun buildUiPrDataPreview() = UiPrData(
    id = 1,
    prTitle = "pr title",
    prDesc = "pr desc",
    user = UserView(),
    closedAt = "",
    createdAt = ""
)
