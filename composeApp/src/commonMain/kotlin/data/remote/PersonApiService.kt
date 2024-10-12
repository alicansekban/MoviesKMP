package data.remote

import io.ktor.client.HttpClient

class PersonApiService(
    private val httpClient: HttpClient
) {
}