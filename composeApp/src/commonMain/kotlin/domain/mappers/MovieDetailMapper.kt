package domain.mappers

import data.response.CastItem
import data.response.MovieDetailResponse
import data.response.MovieReviewResponse
import data.response.MovieReviewResponseItem
import domain.models.MovieCreditsUIModel
import domain.models.MovieDetailUIModel
import domain.models.MovieReviewsItemUIModel
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

fun MovieReviewResponse.toUIModel() : MovieReviewsUIModel {
    return MovieReviewsUIModel(
        page = page ?: 1,
        reviews = results?.map { it.toUIModel() } ?: emptyList(),
        totalPages = total_pages ?: 0,
        totalResults = total_results ?: 0,
        canLoadMore = page != total_pages
    )
}

fun MovieReviewResponseItem.toUIModel(): MovieReviewsItemUIModel {
    return MovieReviewsItemUIModel(
        id = id,
        author = author,
        content = content,
        url = url
    )
}