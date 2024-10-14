package domain.interactors.movie

import data.repository.MoviesRepository
import domain.mappers.movie.toEntity
import domain.mappers.movie.toUIModel
import domain.models.BaseUIModel
import domain.models.movie.MovieCreditsUIModel
import domain.models.movie.MovieDetailUIModel
import domain.models.movie.MovieListUIModel
import domain.models.movie.MovieReviewsUIModel
import domain.models.movie.MovieType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import utils.Constants
import utils.ResultWrapper

class MovieDetailInteractor(
    private val repository: MoviesRepository
) {

    fun getMovieDetails(id: Int): Flow<BaseUIModel<MovieDetailUIModel>> {
        return flow {
            emit(BaseUIModel.Loading)
            repository.getMovieDetail(id).collect { state ->
                when (state) {
                    is ResultWrapper.GenericError -> emit(BaseUIModel.Error(state.error ?: "Error"))
                    ResultWrapper.Loading -> emit(BaseUIModel.Loading)
                    ResultWrapper.NetworkError -> emit(BaseUIModel.Error("Network Error"))
                    is ResultWrapper.Success -> {
                        val uiModel = state.value.toUIModel()
                        emit(BaseUIModel.Success(uiModel))
                    }
                }
            }
        }
    }

    fun getMovieImages(id: Int): Flow<BaseUIModel<List<String>>> {
        return flow {
            emit(BaseUIModel.Loading)
            repository.getMovieImages(id).collect { state ->
                when (state) {
                    is ResultWrapper.GenericError -> emit(BaseUIModel.Error(state.error ?: "Error"))
                    ResultWrapper.Loading -> emit(BaseUIModel.Loading)
                    ResultWrapper.NetworkError -> emit(BaseUIModel.Error("Network Error"))
                    is ResultWrapper.Success -> {
                        val uiModel =
                            state.value.posters?.filter { it.iso_639_1 == "en" }?.take(10)
                                ?.map { Constants.BASE_POSTER_URL + it.file_path.orEmpty() }
                                ?: emptyList()

                        emit(BaseUIModel.Success(uiModel))
                    }
                }
            }
        }
    }

    fun getMovieCredits(id: Int): Flow<BaseUIModel<List<MovieCreditsUIModel>>> {
        return flow {
            emit(BaseUIModel.Loading)
            repository.getMovieCredits(id).collect { state ->
                when (state) {
                    is ResultWrapper.GenericError -> emit(BaseUIModel.Error(state.error ?: "Error"))
                    ResultWrapper.Loading -> emit(BaseUIModel.Loading)
                    ResultWrapper.NetworkError -> emit(BaseUIModel.Error("Network Error"))
                    is ResultWrapper.Success -> {
                        val uiModel = state.value.cast?.map { it.toUIModel() } ?: emptyList()

                        emit(BaseUIModel.Success(uiModel))
                    }
                }
            }
        }
    }

    fun getMovieReviews(
        id: Int,
        page: Int,
        currentModel: MovieReviewsUIModel = MovieReviewsUIModel()
    ): Flow<BaseUIModel<MovieReviewsUIModel>> {
        return flow {
            emit(BaseUIModel.Loading)
            repository.getMovieReviews(id, page).collect { state ->
                when (state) {
                    is ResultWrapper.GenericError -> emit(BaseUIModel.Error(state.error ?: "Error"))
                    ResultWrapper.Loading -> emit(BaseUIModel.Loading)
                    ResultWrapper.NetworkError -> emit(BaseUIModel.Error("Network Error"))
                    is ResultWrapper.Success -> {
                        val uiModel = state.value.toUIModel()

                        emit(BaseUIModel.Success(uiModel))
                    }
                }
            }
        }
    }

    fun getRecommendations(
        id: Int,
        page: Int,
        currentModel: MovieListUIModel
    ): Flow<BaseUIModel<MovieListUIModel>> {
        return flow {
            emit(BaseUIModel.Loading)
            repository.getMovieRecommendations(id, page = page).collect { state ->
                when (state) {
                    is ResultWrapper.GenericError -> emit(BaseUIModel.Error(state.error ?: "Error"))
                    ResultWrapper.Loading -> emit(BaseUIModel.Loading)
                    ResultWrapper.NetworkError -> emit(BaseUIModel.Error("Network Error"))
                    is ResultWrapper.Success -> {
                        val uiModel = state.value.toUIModel(
                            currentModel,
                            movieType = MovieType.RECOMMENDATIONS
                        )
                        emit(BaseUIModel.Success(uiModel))
                    }
                }
            }
        }
    }

    fun getSimilarMovies(
        id: Int,
        page: Int,
        currentModel: MovieListUIModel
    ): Flow<BaseUIModel<MovieListUIModel>> {
        return flow {
            emit(BaseUIModel.Loading)
            repository.getSimilarMovies(id = id, page = page).collect { state ->
                when (state) {
                    is ResultWrapper.GenericError -> emit(BaseUIModel.Error(state.error ?: "Error"))
                    ResultWrapper.Loading -> emit(BaseUIModel.Loading)
                    ResultWrapper.NetworkError -> emit(BaseUIModel.Error("Network Error"))
                    is ResultWrapper.Success -> {
                        val uiModel = state.value.toUIModel(
                            currentModel = currentModel,
                            movieType = MovieType.SIMILAR
                        )
                        emit(BaseUIModel.Success(uiModel))
                    }
                }
            }
        }
    }


    suspend fun onFavoriteClicked(movie: MovieDetailUIModel): MovieDetailUIModel {
        return if (movie.isFavorite) {
            removeMovieFavorite(movie.id ?: 0)
        } else {
            addMovieFavorite(movie)
        }
    }

    private suspend fun addMovieFavorite(movie: MovieDetailUIModel): MovieDetailUIModel {
        val entity = movie.toEntity()
        repository.addMovieFavorite(entity)
        return movie.copy(isFavorite = true)
    }

    private suspend fun removeMovieFavorite(movieId: Int): MovieDetailUIModel {
        repository.removeMovieFromFavorite(movieId)
        return MovieDetailUIModel(isFavorite = false)
    }
}