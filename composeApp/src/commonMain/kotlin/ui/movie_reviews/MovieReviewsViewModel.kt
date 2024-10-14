package ui.movie_reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.interactors.movie.MovieDetailInteractor
import domain.models.BaseUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieReviewsViewModel(
    private val interactor: MovieDetailInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieReviewUIStateModel())
    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(10000L),
        MovieReviewUIStateModel()
    )

    fun getReviews(id: Int, page: Int) {
        viewModelScope.launch {
            interactor.getMovieReviews(id, page, _uiState.value.review).collect { state ->
                if (state is BaseUIModel.Success) {
                    _uiState.value = _uiState.value.copy(review = state.data)
                }
            }
        }
    }

}