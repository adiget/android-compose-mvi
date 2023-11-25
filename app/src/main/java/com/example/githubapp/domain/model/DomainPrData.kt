package com.example.githubapp.domain.model

import com.example.githubapp.domain.model.User

data class DomainPrData(
    var id : Int = -1,
    var desc : String = "",
    var title: String = "",
    var user: User = User(),
    var closedAt: String = "",
    var createdAt: String = ""
) {
    enum class State {
        OPEN, CLOSED, ALL
    }
}