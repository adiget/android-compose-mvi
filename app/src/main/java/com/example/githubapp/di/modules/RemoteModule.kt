package com.example.githubapp.di.modules

import com.example.githubapp.data.datasource.network.GitRemote
import com.example.githubapp.data.datasource.network.GitRemoteRepositoryImp
import com.example.githubapp.network.GithubService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RemoteModule {
    @Binds
    fun bindsGitRemote(
        gitRemoteImp: GitRemoteRepositoryImp
    ): GitRemote
}