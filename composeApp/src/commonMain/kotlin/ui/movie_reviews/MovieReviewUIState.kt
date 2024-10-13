package ui.movie_reviews

import domain.models.movie.MovieReviewsUIModel

data class MovieReviewUIStateModel(
    val review : MovieReviewsUIModel = MovieReviewsUIModel(),
)

