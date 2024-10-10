package ui.movie_detail

import domain.models.MovieListUIModel

data class MovieDetailState(
    val movieId: Int = 0,
    val recommendations: MovieListUIModel = MovieListUIModel(),
    val similarMovies: MovieListUIModel = MovieListUIModel()
)