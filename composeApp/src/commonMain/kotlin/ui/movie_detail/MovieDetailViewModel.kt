package ui.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.interactors.MovieDetailInteractor
import domain.models.BaseUIModel
import domain.models.MovieCreditsUIModel
import domain.models.MovieDetailUIModel
import domain.models.MovieListUIModel
import domain.models.MovieReviewsUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val interactor: MovieDetailInteractor) : ViewModel() {

    private val _movieDetail = MutableStateFlow<BaseUIModel<MovieDetailUIModel>>(BaseUIModel.Empty)
    val movieDetail = _movieDetail.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(10000L),
        BaseUIModel.Empty
    )
    private val _images = MutableStateFlow<BaseUIModel<List<String>>>(BaseUIModel.Empty)
    val images = _images.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(10000L),
        BaseUIModel.Empty
    )
    private val _credits =
        MutableStateFlow<BaseUIModel<List<MovieCreditsUIModel>>>(BaseUIModel.Empty)
    val credits = _credits.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(10000L),
        BaseUIModel.Empty
    )
    private val _reviews =
        MutableStateFlow<BaseUIModel<List<MovieReviewsUIModel>>>(BaseUIModel.Empty)
    val reviews = _reviews.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(10000L),
        BaseUIModel.Empty
    )
    private val _uiState = MutableStateFlow(MovieDetailState())
    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(10000L),
        MovieDetailState()
    )


    fun callApiCalls(id: Int) {
        getMovieImages(id)
        getMovieDetail(id)
        getMovieCredits(id)
        getMovieReviews(id)
        getRecommendations(id, 1, MovieListUIModel())
        getSimilarMovies(id, 1, MovieListUIModel())
    }

    private fun getMovieDetail(id: Int) {
        viewModelScope.launch {
            interactor.getMovieDetails(id).collect {
                _movieDetail.value = it
            }
        }
    }

    private fun getMovieImages(id: Int) {
        viewModelScope.launch {
            interactor.getMovieImages(id).collect {
                _images.value = it
            }
        }
    }

    private fun getMovieCredits(id: Int) {
        viewModelScope.launch {
            interactor.getMovieCredits(id).collect {
                _credits.value = it
            }
        }
    }

    private fun getMovieReviews(id: Int) {
        viewModelScope.launch {
            interactor.getMovieReviews(id, page = 1).collect {
                _reviews.value = it
            }
        }
    }
    fun getRecommendations(id: Int, page: Int, currentModel: MovieListUIModel) {
        viewModelScope.launch {
            interactor.getRecommendations(id = id, page = page, currentModel = currentModel)
                .collect { state ->
                    if (state is BaseUIModel.Success) {
                        _uiState.value = _uiState.value.copy(recommendations = state.data)
                    }
                }
        }
    }

    fun getSimilarMovies(id: Int, page: Int, currentModel: MovieListUIModel) {
        viewModelScope.launch {
            interactor.getSimilarMovies(id = id, page = page, currentModel = currentModel)
                .collect { state ->
                    if (state is BaseUIModel.Success) {
                        _uiState.value = _uiState.value.copy(similarMovies = state.data)
                    }
            }
        }
    }
}