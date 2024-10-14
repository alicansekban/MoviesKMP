package domain.interactors.movie

import data.repository.MoviesRepository
import domain.mappers.movie.toUIModel
import domain.models.BaseUIModel
import domain.models.movie.MovieUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import utils.ResultWrapper

class HomeInteractor(
    private val moviesRepository: MoviesRepository
) {

    fun getUpComingMovies(page: Int): Flow<BaseUIModel<List<MovieUIModel>>> {
        return flow {
            emit(BaseUIModel.Loading)
            moviesRepository.getUpComingMovies(page).collect { state ->
                when (state) {
                    is ResultWrapper.GenericError -> emit(BaseUIModel.Error(state.error ?: "Error"))
                    ResultWrapper.Loading -> emit(BaseUIModel.Loading)
                    ResultWrapper.NetworkError -> emit(BaseUIModel.Error("Network Error"))
                    is ResultWrapper.Success -> {
                        val uiModel = state.value.results?.map {
                            it.toUIModel()
                        } ?: emptyList()
                        emit(BaseUIModel.Success(uiModel))
                    }
                }
            }
        }
    }

    fun getNowPlayingMovies(page: Int): Flow<BaseUIModel<List<MovieUIModel>>> {
        return flow {
            emit(BaseUIModel.Loading)
            moviesRepository.getNowPlayingMovies(page).collect { state ->
                when (state) {
                    is ResultWrapper.GenericError -> emit(BaseUIModel.Error(state.error ?: "Error"))
                    ResultWrapper.Loading -> emit(BaseUIModel.Loading)
                    ResultWrapper.NetworkError -> emit(BaseUIModel.Error("Network Error"))
                    is ResultWrapper.Success -> {
                        val uiModel = state.value.results?.map {
                            it.toUIModel()
                        } ?: emptyList()
                        emit(BaseUIModel.Success(uiModel))
                    }
                }
            }
        }
    }

    fun getPopularMovies(page: Int): Flow<BaseUIModel<List<MovieUIModel>>> {
        return flow {
            emit(BaseUIModel.Loading)
            moviesRepository.getPopularMovies(page).collect { state ->
                when (state) {
                    is ResultWrapper.GenericError -> emit(BaseUIModel.Error(state.error ?: "Error"))
                    ResultWrapper.Loading -> emit(BaseUIModel.Loading)
                    ResultWrapper.NetworkError -> emit(BaseUIModel.Error("Network Error"))
                    is ResultWrapper.Success -> {
                        val uiModel = state.value.results?.map {
                            it.toUIModel()
                        } ?: emptyList()
                        emit(BaseUIModel.Success(uiModel))
                    }
                }
            }
        }
    }

    fun getTopRatedMovies(page: Int): Flow<BaseUIModel<List<MovieUIModel>>> {
        return flow {
            emit(BaseUIModel.Loading)
            moviesRepository.getTopRatedMovies(page).collect { state ->
                when (state) {
                    is ResultWrapper.GenericError -> emit(BaseUIModel.Error(state.error ?: "Error"))
                    ResultWrapper.Loading -> emit(BaseUIModel.Loading)
                    ResultWrapper.NetworkError -> emit(BaseUIModel.Error("Network Error"))
                    is ResultWrapper.Success -> {
                        val uiModel = state.value.results?.map {
                            it.toUIModel()
                        } ?: emptyList()
                        emit(BaseUIModel.Success(uiModel))
                    }
                }
            }
        }
    }

    fun getDiscoverMovies(page: Int): Flow<BaseUIModel<List<MovieUIModel>>> {
        return flow {
            emit(BaseUIModel.Loading)
            moviesRepository.getDiscoverMovies(page).collect { state ->
                when (state) {
                    is ResultWrapper.GenericError -> emit(BaseUIModel.Error(state.error ?: "Error"))
                    ResultWrapper.Loading -> emit(BaseUIModel.Loading)
                    ResultWrapper.NetworkError -> emit(BaseUIModel.Error("Network Error"))
                    is ResultWrapper.Success -> {
                        val uiModel = state.value.results?.map {
                            it.toUIModel()
                        } ?: emptyList()
                        emit(BaseUIModel.Success(uiModel))
                    }
                }
            }
        }
    }
}