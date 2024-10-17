package ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.interactors.movie.SearchMovieInteractor
import domain.models.BaseUIModel
import domain.models.movie.MovieListUIModel
import domain.models.movie.MovieUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchMovieViewModel(
    private val interactor: SearchMovieInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchMovieUIStateModel())
    val uiState = _uiState.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(10000L),
        SearchMovieUIStateModel()
    )

    private val _queryFlow = MutableStateFlow("")
    val queryFlow = _queryFlow.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        ""
    )

    init {
        startSearchFlow()
    }

    @OptIn(FlowPreview::class)
    private fun startSearchFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            queryFlow
                .debounce(1000L) // 1 saniye bekle
                .filter { it.length > 3 } // 3 karakterden uzun sorguları işle
                .distinctUntilChanged() // Aynı sorguyu tekrar tetikleme
                .collectLatest { query ->
                    // Arama işlemi
                    searchMovie(query, 1)
                }
        }
    }

    fun updateUiEvents(event: SearchMovieEvents) {
        when (event) {
            is SearchMovieEvents.OnNextPage -> {
                searchMovie(query = _queryFlow.value, event.page)
            }

            SearchMovieEvents.OnSearch -> {
                searchMovie(_queryFlow.value, 1)
            }

            is SearchMovieEvents.OnSearchQueryChange -> {
                _queryFlow.value = event.query
                if (event.query.isEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        uiModel = MovieListUIModel()
                    )
                }
            }
        }
    }

    private fun searchMovie(query: String, page: Int) {
        viewModelScope.launch {
            interactor.searchMovie(query, page, uiState.value.uiModel).collect { state ->
                when (state) {
                    BaseUIModel.Empty -> {}
                    is BaseUIModel.Error -> {
                        _uiState.value = uiState.value.copy(
                            isPaginating = false
                        )
                    }

                    BaseUIModel.Loading -> {
                        _uiState.value = uiState.value.copy(
                            isPaginating = page != 1
                        )
                    }
                    is BaseUIModel.Success -> {
                        _uiState.value = uiState.value.copy(
                            isPaginating = false,
                            uiModel = state.data

                        )
                    }
                }
            }
        }
    }
    fun onFavoriteClicked(movie: MovieUIModel) {
        viewModelScope.launch {
            interactor.onFavoriteClicked(movie)
        }
    }
}