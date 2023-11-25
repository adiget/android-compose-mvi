package com.example.githubapp.network.mappers

interface EntityMapper<M, E> {
    fun mapFromModel(model: M): E
}
