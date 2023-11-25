package com.example.githubapp.network.mappers

import com.example.githubapp.data.model.GithubUser
import com.example.githubapp.data.model.PullRequestEntity
import com.example.githubapp.network.model.GithubPullRequest
import javax.inject.Inject

class PullRequestEntityMapper @Inject constructor(private val userEntityMapper: UserEntityMapper) :
    EntityMapper<GithubPullRequest, PullRequestEntity> {

    override fun mapFromModel(model: GithubPullRequest): PullRequestEntity {
        return PullRequestEntity(
            id = model.id?:-1,
            desc = model.body?:"No description ",
            user = userEntityMapper.mapFromModel(model.user ?: GithubUser()),
            title = model.title ?:"No title",
            createdAt = model.createdAt ?:"",
            closedAt = model.closedAt ?:""
        )
    }
}