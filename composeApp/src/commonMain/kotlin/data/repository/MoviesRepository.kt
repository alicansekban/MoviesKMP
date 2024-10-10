package data.repository

import data.remote.ApiService
import data.response.BaseMoviesResponse
import data.response.MovieCreditResponse
import data.response.MovieDetailResponse
import data.response.MovieImagesResponse
import data.response.MovieReviewResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import utils.ResultWrapper

class MoviesRepository(private val apiService: ApiService) {

    fun getDiscoverMovies(page: Int): Flow<ResultWrapper<BaseMoviesResponse>> =
        flow { emit(apiService.getDiscoverMovies(page)) }.flowOn(Dispatchers.IO)

    fun getUpComingMovies(page: Int): Flow<ResultWrapper<BaseMoviesResponse>> =
        flow { emit(apiService.getUpComingMovies(page)) }.flowOn(Dispatchers.IO)


    fun getNowPlayingMovies(page: Int): Flow<ResultWrapper<BaseMoviesResponse>> =
        flow { emit(apiService.getNowPlayingMovies(page)) }.flowOn(Dispatchers.IO)

    fun getPopularMovies(page: Int): Flow<ResultWrapper<BaseMoviesResponse>> =
        flow { emit(apiService.getPopularMovies(page)) }.flowOn(Dispatchers.IO)

    fun getTopRatedMovies(page: Int): Flow<ResultWrapper<BaseMoviesResponse>> =
        flow { emit(apiService.getTopRatedMovies(page)) }.flowOn(Dispatchers.IO)

    fun searchMovie(page: Int, query: String): Flow<ResultWrapper<BaseMoviesResponse>> =
        flow { emit(apiService.searchMovie(page, query)) }.flowOn(Dispatchers.IO)

    fun getMovieDetail(id: Int): Flow<ResultWrapper<MovieDetailResponse>> =
        flow { emit(apiService.getMovieDetail(id)) }.flowOn(Dispatchers.IO)

    fun getMovieReviews(id: Int, page: Int): Flow<ResultWrapper<MovieReviewResponse>> =
        flow { emit(apiService.getMovieReviews(id, page)) }.flowOn(Dispatchers.IO)

    fun getMovieImages(id: Int): Flow<ResultWrapper<MovieImagesResponse>> =
        flow { emit(apiService.getMovieImages(id)) }.flowOn(Dispatchers.IO)

    fun getMovieCredits(id: Int): Flow<ResultWrapper<MovieCreditResponse>> =
        flow { emit(apiService.getMovieCredits(id)) }.flowOn(Dispatchers.IO)

    fun getMovieRecommendations(id: Int, page: Int): Flow<ResultWrapper<BaseMoviesResponse>> =
        flow { emit(apiService.getMovieRecommendations(id, page)) }.flowOn(Dispatchers.IO)

    fun getSimilarMovies(id: Int, page: Int): Flow<ResultWrapper<BaseMoviesResponse>> =
        flow { emit(apiService.getSimilarMovies(id = id, page = page)) }.flowOn(Dispatchers.IO)


}