package ui.search

import domain.models.movie.MovieListUIModel

sealed interface SearchMovieEvents {
    data class OnSearchQueryChange(val query: String) : SearchMovieEvents
    data object OnSearch : SearchMovieEvents
    data class OnNextPage(val page: Int) : SearchMovieEvents

}

data class SearchMovieUIStateModel(
    val uiModel: MovieListUIModel = MovieListUIModel(),
    val isPaginating: Boolean = false,
)