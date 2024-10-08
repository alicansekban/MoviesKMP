package domain.mappers

import data.response.CastItem
import data.response.MovieDetailResponse
import data.response.MovieReviewResponseItem
import domain.models.MovieCreditsUIModel
import domain.models.MovieDetailUIModel
import domain.models.MovieReviewsUIModel
import utils.Constants

fun MovieDetailResponse.toUIModel(): MovieDetailUIModel {
    return MovieDetailUIModel(
        id = id,
        title = title,
        imageUrl = Constants.BASE_POSTER_URL + this.poster_path,
        overview = overview,
        duration = "${this.runtime} min.",
        voteAvg = vote_average.toString().take(3),
        releaseDate = release_date
    )
}

fun CastItem.toUIModel(): MovieCreditsUIModel {
    return MovieCreditsUIModel(
        id = id,
        characterName = character,
        imageUrl = Constants.BASE_POSTER_URL + this.profile_path,
        name = name
    )
}

fun MovieReviewResponseItem.toUIModel(): MovieReviewsUIModel {
    return MovieReviewsUIModel(
        id = id,
        author = author,
        content = content,
        url = url
    )
}