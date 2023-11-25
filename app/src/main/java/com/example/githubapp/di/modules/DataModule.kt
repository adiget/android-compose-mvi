package com.example.githubapp.di.modules

import com.example.githubapp.data.repository.GithubRepositoryImpl
import com.example.githubapp.domain.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsGitRepository(
        gitRepository: GithubRepositoryImpl
    ): GithubRepository
}
