package domain.interactors

import data.repository.MoviesRepository
import domain.mappers.toUIModel
import domain.models.BaseUIModel
import domain.models.MovieListUIModel
import domain.models.MovieType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import utils.ResultWrapper

class MovieListInteractor(
    private val repository: MoviesRepository
) {

    fun getMoviesByType(
        movieType: MovieType,
        page: Int,
        currentModel: MovieListUIModel
    ): Flow<BaseUIModel<MovieListUIModel>> {
        return flow {
            when (movieType) {
                MovieType.UPCOMING -> {
                    emitAll(getUpComingMovies(page, currentModel))
                }

                MovieType.NOW_PLAYING -> {
                    emitAll(getNowPlayingMovies(page, currentModel))
                }

                MovieType.TOP_RATED -> {
                    emitAll(getTopRatedMovies(page, currentModel))
                }

                MovieType.POPULAR -> {
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
                        val uiModel = state.value.toUIModel(currentModel)
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
                        val uiModel = state.value.toUIModel(currentModel)
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
                        val uiModel = state.value.toUIModel(currentModel)
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
                        val uiModel = state.value.toUIModel(currentModel)
                        emit(BaseUIModel.Success(uiModel))
                    }
                }
            }
        }
    }
}