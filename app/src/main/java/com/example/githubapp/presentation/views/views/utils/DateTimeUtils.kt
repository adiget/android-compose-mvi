package com.example.githubapp.presentation.views.views.utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


object DateTimeUtils {

    const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssX"
    const val PATTERN = "dd MMM''yy"

    fun getDayWithMonthName(input: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
        val outputFormatter = DateTimeFormatter.ofPattern(PATTERN)

        val zonedDateTime = ZonedDateTime.parse(input, inputFormatter)

        return zonedDateTime.format(outputFormatter)
    }
}