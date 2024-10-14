package ui.search

import domain.models.movie.MovieListUIModel

sealed interface SearchMovieEvents {
    data class OnSearchQueryChange(val query: String) : SearchMovieEvents
    data object OnSearch : SearchMovieEvents
    data class OnNextPage(val page: Int) : SearchMovieEvents

}

data class SearchMovieUIStateModel(
    val query: String = "",
    val uiModel: MovieListUIModel = MovieListUIModel(),
    val isLoading: Boolean = false,
    val isPaginating: Boolean = false,
)