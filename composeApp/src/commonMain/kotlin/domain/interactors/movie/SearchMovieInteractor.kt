package domain.interactors.movie

import data.repository.MoviesRepository
import domain.mappers.movie.toEntity
import domain.mappers.movie.toUIModel
import domain.models.BaseUIModel
import domain.models.movie.MovieListUIModel
import domain.models.movie.MovieType
import domain.models.movie.MovieUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
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
            emitAll(
                combine(
                    searchRemoteMovie(query, page, currentModel),
                    repository.getFavoriteMovies()
                ) { remote, local ->
                    when (remote) {
                        BaseUIModel.Empty -> BaseUIModel.Empty
                        is BaseUIModel.Error -> BaseUIModel.Error(remote.message)
                        BaseUIModel.Loading -> BaseUIModel.Loading
                        is BaseUIModel.Success -> {
                            val updatedMovies = remote.data.movies.map { movie ->
                                movie.copy(isFavorite = local.any { it.movieId == movie.id })
                            }
                            BaseUIModel.Success(remote.data.copy(movies = updatedMovies))
                        }
                    }
                }
            )
        }
    }

    private fun searchRemoteMovie(
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
                            movieType = MovieType.SEARCH
                        )
                        emit(BaseUIModel.Success(uiModel))
                    }
                }
            }

        }
    }

    suspend fun onFavoriteClicked(movie: MovieUIModel) {
        if (movie.isFavorite) {
            removeMovieFavorite(movie)
        } else {
            addMovieFavorite(movie)
        }
    }

    private suspend fun addMovieFavorite(movie: MovieUIModel) {
        val entity = movie.toEntity()
        repository.addMovieFavorite(entity)

    }

    private suspend fun removeMovieFavorite(movie: MovieUIModel) {
        repository.removeMovieFromFavorite(movie.id ?: 0)
    }


}