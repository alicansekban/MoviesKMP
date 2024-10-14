package ui.movie_detail

import domain.models.movie.MovieListUIModel

data class MovieDetailState(
    val movieId: Int = 0,
    val recommendations: MovieListUIModel = MovieListUIModel(),
    val similarMovies: MovieListUIModel = MovieListUIModel(),
    val showUi: Boolean = false,
)