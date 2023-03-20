package com.blueberry.kmp_apod.dates

import kotlinx.datetime.LocalDate

data class AstronomyDate(val month: String, val date: String, val day: String, val isSelected: Boolean, val dateInfo: LocalDate)