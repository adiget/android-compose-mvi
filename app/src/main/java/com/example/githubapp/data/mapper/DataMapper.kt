package com.example.githubapp.data.mapper

interface DataMapper<E, D> {

    fun mapFromEntity(type: E): D
}
