package domain.interactors.movie

import data.local.entity.MovieEntity
import data.repository.MoviesRepository

class FavoritesInteractor(
    private val repository: MoviesRepository
) {

    fun getFavoriteMovies() = repository.getFavoriteMovies()

    suspend fun addMovieFavorite(movieEntity: MovieEntity) {
        repository.addMovieFavorite(movieEntity)
    }
}