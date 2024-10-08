package domain.interactors

import data.repository.MoviesRepository
import domain.mappers.toUIModel
import domain.models.BaseUIModel
import domain.models.MovieListUIModel
import domain.models.MovieType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import utils.ResultWrapper

class SearchMovieInteractor(
    private val repository: MoviesRepository
) {

    fun searchMovie(
        query: String,
        page: Int,
        currentModel: MovieListUIModel
    ): Flow<BaseUIModel<MovieListUIModel>> {
        return flow {
            emit(BaseUIModel.Loading)

            repository.searchMovie(page, query).collect { state ->
                when (state) {
                    is ResultWrapper.GenericError -> emit(BaseUIModel.Error(state.error ?: "Error"))
                    ResultWrapper.Loading -> emit(BaseUIModel.Loading)
                    ResultWrapper.NetworkError -> emit(BaseUIModel.Error("Network Error"))
                    is ResultWrapper.Success -> {
                        val model = if (page == 1) {
                            MovieListUIModel()
                        } else {
                            currentModel
                        }
                        val uiModel = state.value.toUIModel(
                            model,
                            movieType = MovieType.UPCOMING
                        )
                        emit(BaseUIModel.Success(uiModel))
                    }
                }
            }

        }
    }


}