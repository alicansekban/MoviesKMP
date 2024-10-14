package domain.interactors.movie

import data.repository.MoviesRepository
import domain.mappers.movie.toUIModel
import domain.models.movie.MovieUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesInteractor(
    private val repository: MoviesRepository
) {

    fun getFavoriteMovies(): Flow<List<MovieUIModel>> {
        return repository.getFavoriteMovies().map { list -> list.map { it.toUIModel() } }
    }

    suspend fun removeMovieFavorite(movieId: Int) {
        repository.removeMovieFromFavorite(movieId)
    }
}