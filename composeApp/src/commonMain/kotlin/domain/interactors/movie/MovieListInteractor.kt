package domain.interactors.movie

import data.repository.MoviesRepository
import domain.mappers.movie.toUIModel
import domain.models.BaseUIModel
import domain.models.movie.MovieListUIModel
import domain.models.movie.MovieType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import utils.ResultWrapper

class MovieListInteractor(
    private val repository: MoviesRepository
) {

    fun getMoviesByType(
        movieType: String,
        page: Int,
        currentModel: MovieListUIModel
    ): Flow<BaseUIModel<MovieListUIModel>> {
        return flow {
            when (movieType) {
                MovieType.UPCOMING.type -> {
                    emitAll(getUpComingMovies(page, currentModel))
                }

                MovieType.NOW_PLAYING.type -> {
                    emitAll(getNowPlayingMovies(page, currentModel))
                }

                MovieType.TOP_RATED.type -> {
                    emitAll(getTopRatedMovies(page, currentModel))
                }

                MovieType.POPULAR.type -> {
                    emitAll(getPopularMovies(page, currentModel))
                }
            }
        }
    }

    private fun getUpComingMovies(
        page: Int,
        currentModel: MovieListUIModel
    ): Flow<BaseUIModel<MovieListUIModel>> {
        return flow {
            emit(BaseUIModel.Loading)
            repository.getUpComingMovies(page).collect { state ->
                when (state) {
                    is ResultWrapper.GenericError -> emit(BaseUIModel.Error(state.error ?: "Error"))
                    ResultWrapper.Loading -> emit(BaseUIModel.Loading)
                    ResultWrapper.NetworkError -> emit(BaseUIModel.Error("Network Error"))
                    is ResultWrapper.Success -> {
                        val uiModel = state.value.toUIModel(
                            currentModel,
                            movieType = MovieType.UPCOMING
                        )
                        emit(BaseUIModel.Success(uiModel))
                    }
                }
            }
        }
    }

    private fun getNowPlayingMovies(
        page: Int,
        currentModel: MovieListUIModel
    ): Flow<BaseUIModel<MovieListUIModel>> {
        return flow {

            emit(BaseUIModel.Loading)
            repository.getNowPlayingMovies(page).collect { state ->
                when (state) {
                    is ResultWrapper.GenericError -> emit(BaseUIModel.Error(state.error ?: "Error"))
                    ResultWrapper.Loading -> emit(BaseUIModel.Loading)
                    ResultWrapper.NetworkError -> emit(BaseUIModel.Error("Network Error"))
                    is ResultWrapper.Success -> {
                        val uiModel = state.value.toUIModel(
                            currentModel,
                            movieType = MovieType.NOW_PLAYING
                        )
                        emit(BaseUIModel.Success(uiModel))
                    }
                }
            }
        }
    }

    private fun getTopRatedMovies(
        page: Int,
        currentModel: MovieListUIModel
    ): Flow<BaseUIModel<MovieListUIModel>> {
        return flow {

            emit(BaseUIModel.Loading)
            repository.getTopRatedMovies(page).collect { state ->
                when (state) {
                    is ResultWrapper.GenericError -> emit(BaseUIModel.Error(state.error ?: "Error"))
                    ResultWrapper.Loading -> emit(BaseUIModel.Loading)
                    ResultWrapper.NetworkError -> emit(BaseUIModel.Error("Network Error"))
                    is ResultWrapper.Success -> {
                        val uiModel = state.value.toUIModel(
                            currentModel,

                            movieType = MovieType.TOP_RATED
                        )
                        emit(BaseUIModel.Success(uiModel))
                    }
                }
            }
        }
    }

    private fun getPopularMovies(
        page: Int,
        currentModel: MovieListUIModel
    ): Flow<BaseUIModel<MovieListUIModel>> {
        return flow {

            emit(BaseUIModel.Loading)
            repository.getPopularMovies(page).collect { state ->
                when (state) {
                    is ResultWrapper.GenericError -> emit(BaseUIModel.Error(state.error ?: "Error"))
                    ResultWrapper.Loading -> emit(BaseUIModel.Loading)
                    ResultWrapper.NetworkError -> emit(BaseUIModel.Error("Network Error"))
                    is ResultWrapper.Success -> {
                        val uiModel = state.value.toUIModel(
                            currentModel,
                            movieType = MovieType.POPULAR
                        )
                        emit(BaseUIModel.Success(uiModel))
                    }
                }
            }
        }
    }
}