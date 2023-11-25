package com.example.githubapp.network

object NetworkingConstants {
    const val BASE_URL = "https://api.github.com/"
    const val GET_USERS = "users"
    const val GET_USER = "users/{userLogin}"
    const val GET_REPOS_BY_USER = "users/{username}/repos"
    const val GET_PRS_BY_USER_REPO = "/repos/{username}/{repo_name}/pulls"
}