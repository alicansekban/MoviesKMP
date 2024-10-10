package data.remote

import data.response.BaseMoviesResponse
import data.response.MovieCreditResponse
import data.response.MovieDetailResponse
import data.response.MovieImagesResponse
import data.response.MovieReviewResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import utils.Constants
import utils.ResultWrapper
import utils.safeApiCall

class ApiService(
    private val client: HttpClient
) {

    suspend fun getDiscoverMovies(page: Int): ResultWrapper<BaseMoviesResponse> =
        safeApiCall(Dispatchers.IO) {
            client.get(urlString = Constants.BASE_URL) {
                url {
                    appendPathSegments("discover", "movie")
                    parameters.append("page", page.toString())
                }
            }.body()
        }

    suspend fun getUpComingMovies(page: Int): ResultWrapper<BaseMoviesResponse> =
        safeApiCall(Dispatchers.IO) {
            client.get(urlString = Constants.BASE_URL) {
                url {
                    appendPathSegments("movie", "upcoming")
                    parameters.append("page", page.toString())
                }
            }.body()
        }

    suspend fun getPopularMovies(page: Int): ResultWrapper<BaseMoviesResponse> =
        safeApiCall(Dispatchers.IO) {
            client.get(urlString = Constants.BASE_URL) {
                url {
                    appendPathSegments("movie", "popular")
                    parameters.append("page", page.toString())
                }
            }.body()
        }

    suspend fun getTopRatedMovies(page: Int): ResultWrapper<BaseMoviesResponse> =
        safeApiCall(Dispatchers.IO) {
            client.get(urlString = Constants.BASE_URL) {
                url {
                    appendPathSegments("movie", "top_rated")
                    parameters.append("page", page.toString())
                }
            }.body()
        }

    suspend fun getNowPlayingMovies(page: Int): ResultWrapper<BaseMoviesResponse> =
        safeApiCall(Dispatchers.IO) {
            client.get(urlString = Constants.BASE_URL) {
                url {
                    appendPathSegments("movie", "now_playing")
                    parameters.append("page", page.toString())
                }
            }.body()
        }

    suspend fun searchMovie(page: Int, query: String): ResultWrapper<BaseMoviesResponse> =
        safeApiCall(Dispatchers.IO) {
            client.get(urlString = Constants.BASE_URL) {
                url {
                    // Path param -> ..
                    appendPathSegments("search", "movie")
                    // Query param -> ..?page=page
                    parameters.append("page", page.toString())
                    parameters.append("query", query)
                }
            }.body()
        }

    suspend fun getMovieDetail(id: Int): ResultWrapper<MovieDetailResponse> =
        safeApiCall(Dispatchers.IO) {
            client.get("movie/$id").body()
        }

    suspend fun getMovieReviews(id: Int, page: Int): ResultWrapper<MovieReviewResponse> =
        safeApiCall(Dispatchers.IO) {
            client.get(urlString = Constants.BASE_URL) {
                url {
                    // Path param -> ..
                    appendPathSegments("movie", id.toString(), "reviews")

                    // Query param -> ..?page=page
                    parameters.append("page", page.toString())
                }
            }.body()
        }

    suspend fun getMovieCredits(id: Int): ResultWrapper<MovieCreditResponse> =
        safeApiCall(Dispatchers.IO) {
            client.get(urlString = Constants.BASE_URL) {
                url {
                    appendPathSegments("movie", id.toString(), "credits")
                }
            }.body()
        }

    suspend fun getMovieImages(id: Int): ResultWrapper<MovieImagesResponse> =
        safeApiCall(Dispatchers.IO) {
            client.get(urlString = Constants.BASE_URL) {
                url {
                    appendPathSegments("movie", id.toString(), "images")
                }
            }.body()
        }

    suspend fun getMovieRecommendations(id: Int, page: Int): ResultWrapper<BaseMoviesResponse> =
        safeApiCall(Dispatchers.IO) {
            client.get(urlString = Constants.BASE_URL) {
                url {
                    appendPathSegments("movie", id.toString(), "recommendations")
                    parameters.append("page", page.toString())

                }
            }.body()
        }

    suspend fun getSimilarMovies(id: Int, page: Int): ResultWrapper<BaseMoviesResponse> =
        safeApiCall(Dispatchers.IO) {
            client.get(urlString = Constants.BASE_URL) {
                url {
                    appendPathSegments("movie", id.toString(), "similar")
                    parameters.append("page", page.toString())
                }
            }.body()
        }

}