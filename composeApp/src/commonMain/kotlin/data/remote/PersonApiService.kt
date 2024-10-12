package data.remote

import data.response.movie.MovieReviewResponse
import data.response.person.PersonDetailResponse
import data.response.person.PersonImageResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import utils.Constants
import utils.ResultWrapper
import utils.safeApiCall

class PersonApiService(
    private val client: HttpClient
) {
    suspend fun getPersonDetail(id: Int): ResultWrapper<PersonDetailResponse> =
        safeApiCall(Dispatchers.IO) {
            client.get(urlString = Constants.BASE_URL) {
                url {
                    // Path param -> ..
                    appendPathSegments("person", id.toString())

                }
            }.body()
        }

    suspend fun getPersonImages(id: Int): ResultWrapper<PersonImageResponse> =
        safeApiCall(Dispatchers.IO) {
            client.get(urlString = Constants.BASE_URL) {
                url {
                    // Path param -> ..
                    appendPathSegments("person", id.toString(),"images")

                }
            }.body()
        }
}