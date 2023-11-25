package com.example.githubapp.ui

interface ViewMapper<out V, in D> {

    fun mapToView(type: D): V
}
