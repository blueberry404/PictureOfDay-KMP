package com.blueberry.kmp_apod.utils

expect class PlatformUtil() {
    fun getFormattedDate(
        date: String,
        currentFormat: String,
        expectedFormat: String
    ): String
}