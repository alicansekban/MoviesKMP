package ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.interactors.movie.FavoritesInteractor
import domain.models.movie.MovieUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FavoriteMoviesViewModel(
    private val interactor: FavoritesInteractor
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<MovieUIModel>>(emptyList())
    val favorites: MutableStateFlow<List<MovieUIModel>> = _favorites


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

    fun onFavoriteIconClicked(movieId: Int) {
        viewModelScope.launch {
            interactor.removeMovieFavorite(movieId)
        }
    }
}