package com.example.githubapp.data.datasource

import javax.inject.Inject

class GitDataSourceFactory @Inject constructor(
    private val githubAppRemoteDataSource: GithubAppNetworkDataSource
) {

    fun getRemoteDataSource(): GithubAppDataSource {
        return githubAppRemoteDataSource
    }
}