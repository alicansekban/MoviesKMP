package ui.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.interactors.movie.MovieListInteractor
import domain.models.BaseUIModel
import domain.models.movie.MovieListUIModel
import domain.models.movie.MovieUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val interactor: MovieListInteractor
) : ViewModel() {


    private val _movies =
        MutableStateFlow(MovieListUIStateModel())
    val movies = _movies.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(10000L),
        MovieListUIStateModel()
    )

    fun updateEvents(event: MovieListUIEvents) {
        when (event) {
            MovieListUIEvents.GetNextPage -> {
                getMoviesByType(
                    page = _movies.value.uiModel.page.plus(1), currentModel = _movies.value.uiModel,
                    movieType = _movies.value.uiModel.movieType.type
                )
            }
        }
    }

    fun getMoviesByType(movieType: String, page: Int, currentModel: MovieListUIModel) {
        viewModelScope.launch {
            interactor.getMoviesByType(
                movieType = movieType,
                page = page,
                currentModel = currentModel
            ).collect { state ->
                if (state is BaseUIModel.Success) {
                    _movies.value = _movies.value.copy(uiModel = state.data)
                }
                _movies.value = _movies.value.copy(isLoading = state is BaseUIModel.Loading)

            }
        }
    }
    fun onFavoriteClicked(movie: MovieUIModel) {
        viewModelScope.launch {
            interactor.onFavoriteClicked(movie)
        }
    }

}