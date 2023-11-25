package com.example.githubapp.data.mapper

import com.example.githubapp.data.model.PullRequestEntity
import com.example.githubapp.domain.model.DomainPrData
import com.example.githubapp.domain.model.User
import javax.inject.Inject

class PrDataMapper @Inject constructor() : DataMapper<PullRequestEntity, DomainPrData> {

    override fun mapFromEntity(type: PullRequestEntity): DomainPrData {
        return DomainPrData(
            id = type.id,
            desc = type.desc,
            closedAt = type.closedAt,
            createdAt = type.createdAt,
            title = type.title,
            user = User(type.user.userName, type.user.profilePic)
        )
    }
}