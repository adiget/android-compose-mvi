package com.example.githubapp.data.model

data class SingleRepoEntity (
    var repoName : String = "",
    var repoDescription : String = "",
    var openIssuesCount : Int = -1,
    var forksCount : Int = -1,
    var defaultBranch : String = ""
)