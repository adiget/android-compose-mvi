package com.example.githubapp.ui.repos.model

data class UiRepoData(
    var repoName : String = "",
    var repoDescription : String = "",
    var openIssuesCount : Int = -1,
    var forksCount : Int = -1,
    var defaultBranch : String = ""
)

fun buildUiRepoDataPreview() = UiRepoData(
    repoName = "repo name",
    repoDescription = "repo desc",
    openIssuesCount = 0,
    forksCount = 1,
    defaultBranch = "default branch"
)
