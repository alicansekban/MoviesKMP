package domain.interactors

import data.repository.MoviesRepository
import domain.mappers.toUIModel
import domain.models.BaseUIModel
import domain.models.MovieCreditsUIModel
import domain.models.MovieDetailUIModel
import domain.models.MovieReviewsUIModel
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

    fun getMovieReviews(id: Int, page: Int): Flow<BaseUIModel<List<MovieReviewsUIModel>>> {
        return flow {
            emit(BaseUIModel.Loading)
            repository.getMovieReviews(id, page).collect { state ->
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