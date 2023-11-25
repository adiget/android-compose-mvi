package com.example.githubapp.network

import com.example.githubapp.data.model.GithubUser
import com.example.githubapp.network.NetworkingConstants.BASE_URL
import com.example.githubapp.network.NetworkingConstants.GET_PRS_BY_USER_REPO
import com.example.githubapp.network.NetworkingConstants.GET_REPOS_BY_USER
import com.example.githubapp.network.NetworkingConstants.GET_USERS
import com.example.githubapp.network.model.GithubPullRequest
import com.example.githubapp.network.model.GithubSingleRepo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @GET(GET_USERS)
    suspend fun getUsers(): List<GithubUser>

    @GET(GET_REPOS_BY_USER)
    suspend fun getUserRepositories(
        @Path("username") username: String
    ): List<GithubSingleRepo>

    @GET(GET_PRS_BY_USER_REPO)
    suspend fun getPullRequestForGithubRepo(
        @Path("username") username: String,
        @Path("repo_name") repoName: String,
        @Query("state") state: String = "all"
    ) : List<GithubPullRequest>

    companion object {
        fun create(): GithubService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubService::class.java)
        }
    }
}