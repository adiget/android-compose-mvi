package com.example.githubapp.network.mappers

import com.example.githubapp.data.model.GithubUser
import com.example.githubapp.data.model.UserEntity
import javax.inject.Inject

class UserEntityMapper @Inject constructor() : EntityMapper<GithubUser, UserEntity> {

    override fun mapFromModel(model: GithubUser): UserEntity {
        return UserEntity(
            userName = model.login?:"",
            profilePic = model.avatarUrl?:""
        )
    }
}