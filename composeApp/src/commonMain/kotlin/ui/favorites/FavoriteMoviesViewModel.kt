package ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.local.entity.MovieEntity
import domain.interactors.movie.FavoritesInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FavoriteMoviesViewModel(
    private val interactor: FavoritesInteractor
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<MovieEntity>>(emptyList())
    val favorites: MutableStateFlow<List<MovieEntity>> = _favorites


    init {
        getFavoriteMovies()
    }

    private fun getFavoriteMovies() {
        viewModelScope.launch {
            interactor.getFavoriteMovies().collect {
                _favorites.value = it
            }
        }
    }
}