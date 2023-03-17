package com.blueberry.kmp_apod.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class AstronomyPictureService {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                explicitNulls = false
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getPictureOfDay(date: String): AstronomyPicture {
        val response = client.get(APOD_URL) {
            url {
                parameters.append("api_key", "<API key here>")
                parameters.append("date", date)
            }
        }.body<AstronomyPicture>()
        return response
    }

    companion object {
        private const val APOD_URL = "https://api.nasa.gov/planetary/apod"
    }
}