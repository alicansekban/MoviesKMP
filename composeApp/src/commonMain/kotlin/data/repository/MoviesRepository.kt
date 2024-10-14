package data.repository

import data.local.AppDatabase
import data.local.entity.MovieEntity
import data.remote.MoviesApiService
import data.response.movie.BaseMoviesResponse
import data.response.movie.MovieCreditResponse
import data.response.movie.MovieDetailResponse
import data.response.movie.MovieImagesResponse
import data.response.movie.MovieReviewResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import utils.ResultWrapper

class MoviesRepository(
    private val moviesApiService: MoviesApiService,
    private val localDb: AppDatabase
) {

    suspend fun addMovieFavorite(movieEntity: MovieEntity) {
        localDb.getDao().insertMovie(movieEntity)
    }
    suspend fun removeMovieFromFavorite(movieId: Int) {
        localDb.getDao().removeMovie(movieId)
    }

    fun getFavoriteMovies(): Flow<List<MovieEntity>> {
        return localDb.getDao().getAllMovies()
    }

    suspend fun isMovieFavorite(movieId: Int): Boolean {
        val movie = localDb.getDao().getMovieById(movieId)
        return movie != null
    }

    fun getDiscoverMovies(page: Int): Flow<ResultWrapper<BaseMoviesResponse>> =
        flow { emit(moviesApiService.getDiscoverMovies(page)) }.flowOn(Dispatchers.IO)

    fun getUpComingMovies(page: Int): Flow<ResultWrapper<BaseMoviesResponse>> =
        flow { emit(moviesApiService.getUpComingMovies(page)) }.flowOn(Dispatchers.IO)


    fun getNowPlayingMovies(page: Int): Flow<ResultWrapper<BaseMoviesResponse>> =
        flow { emit(moviesApiService.getNowPlayingMovies(page)) }.flowOn(Dispatchers.IO)

    fun getPopularMovies(page: Int): Flow<ResultWrapper<BaseMoviesResponse>> =
        flow { emit(moviesApiService.getPopularMovies(page)) }.flowOn(Dispatchers.IO)

    fun getTopRatedMovies(page: Int): Flow<ResultWrapper<BaseMoviesResponse>> =
        flow { emit(moviesApiService.getTopRatedMovies(page)) }.flowOn(Dispatchers.IO)

    fun searchMovie(page: Int, query: String): Flow<ResultWrapper<BaseMoviesResponse>> =
        flow { emit(moviesApiService.searchMovie(page, query)) }.flowOn(Dispatchers.IO)

    fun getMovieDetail(id: Int): Flow<ResultWrapper<MovieDetailResponse>> =
        flow { emit(moviesApiService.getMovieDetail(id)) }.flowOn(Dispatchers.IO)

    fun getMovieReviews(id: Int, page: Int): Flow<ResultWrapper<MovieReviewResponse>> =
        flow { emit(moviesApiService.getMovieReviews(id, page)) }.flowOn(Dispatchers.IO)

    fun getMovieImages(id: Int): Flow<ResultWrapper<MovieImagesResponse>> =
        flow { emit(moviesApiService.getMovieImages(id)) }.flowOn(Dispatchers.IO)

    fun getMovieCredits(id: Int): Flow<ResultWrapper<MovieCreditResponse>> =
        flow { emit(moviesApiService.getMovieCredits(id)) }.flowOn(Dispatchers.IO)

    fun getMovieRecommendations(id: Int, page: Int): Flow<ResultWrapper<BaseMoviesResponse>> =
        flow { emit(moviesApiService.getMovieRecommendations(id, page)) }.flowOn(Dispatchers.IO)

    fun getSimilarMovies(id: Int, page: Int): Flow<ResultWrapper<BaseMoviesResponse>> =
        flow {
            emit(
                moviesApiService.getSimilarMovies(
                    id = id,
                    page = page
                )
            )
        }.flowOn(Dispatchers.IO)


}