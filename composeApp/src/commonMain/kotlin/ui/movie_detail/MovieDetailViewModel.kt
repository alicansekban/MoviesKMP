package ui.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.interactors.MovieDetailInteractor
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val interactor: MovieDetailInteractor) : ViewModel() {


    fun callApiCalls(id: Int) {
        getMovieImages(id)
        getMovieDetail(id)
        getMovieCredits(id)
        getMovieReviews(id)

    }

    private fun getMovieDetail(id: Int) {
        viewModelScope.launch {
            interactor.getMovieDetails(id).collect {

            }
        }
    }

    private fun getMovieImages(id: Int) {
        viewModelScope.launch {
            interactor.getMovieImages(id).collect {
            }
        }
    }

    private fun getMovieCredits(id: Int) {
        viewModelScope.launch {
            interactor.getMovieCredits(id).collect {
            }
        }
    }

    private fun getMovieReviews(id: Int) {
        viewModelScope.launch {
            interactor.getMovieReviews(id, page = 1).collect {
            }
        }

    }
}