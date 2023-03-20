package com.blueberry.kmp_apod.utils

import platform.Foundation.NSDateFormatter

actual class PlatformUtil actual constructor() {
    actual fun getFormattedDate(
        date: String,
        currentFormat: String,
        expectedFormat: String
    ): String {

        val receivedDate = NSDateFormatter()
            .apply { this.dateFormat = currentFormat }
            .run { dateFromString(date) }

        return NSDateFormatter()
            .apply { dateFormat = expectedFormat }
            .run { receivedDate?.let { stringFromDate(it) } }
            ?: date
    }
}