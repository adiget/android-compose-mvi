package com.example.githubapp.ui

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TestUseCase<T> {

    /**
     * The backing hot flow for the value for testing.
     */
    private val uiStateFlow =
        MutableSharedFlow<T>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    operator fun invoke(): Flow<T> {
        return uiStateFlow
    }

    /**
     * A test-only API to allow controlling the value from tests.
     */
    suspend fun sendUsers(value: T) {
        uiStateFlow.emit(value)
    }
}