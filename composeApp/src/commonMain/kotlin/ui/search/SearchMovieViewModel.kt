package ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.interactors.SearchMovieInteractor
import domain.models.BaseUIModel
import domain.models.MovieListUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchMovieViewModel(
    private val interactor: SearchMovieInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchMovieUIStateModel())
    val uiState = _uiState.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(10000L),
        SearchMovieUIStateModel()
    )

    fun updateUiEvents(event: SearchMovieEvents) {
        when (event) {
            is SearchMovieEvents.OnNextPage -> {
                searchMovie(uiState.value.query, event.page)
            }

            SearchMovieEvents.OnSearch -> {
                searchMovie(uiState.value.query, 1)
            }

            is SearchMovieEvents.OnSearchQueryChange -> {
                _uiState.value = uiState.value.copy(
                    query = event.query
                )
                if (_uiState.value.query.length > 3 && _uiState.value.isLoading.not() && _uiState.value.isPaginating.not()) {
                    searchMovie(uiState.value.query, 1)
                    return
                }
                if (_uiState.value.query.isEmpty()) {
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
                            isLoading = false,
                            isPaginating = false
                        )
                    }

                    BaseUIModel.Loading -> {}
                    is BaseUIModel.Success -> {
                        _uiState.value = uiState.value.copy(
                            isLoading = false,
                            isPaginating = false,
                            uiModel = state.data

                        )
                    }
                }
            }

        }
    }
}