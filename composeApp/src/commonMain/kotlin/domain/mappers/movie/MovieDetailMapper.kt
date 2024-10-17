package domain.mappers.movie

import data.local.entity.MovieEntity
import data.response.movie.CastItem
import data.response.movie.MovieDetailResponse
import data.response.movie.MovieReviewResponse
import data.response.movie.MovieReviewResponseItem
import data.response.movie.MovieVideoItem
import domain.models.movie.MovieCreditsUIModel
import domain.models.movie.MovieDetailUIModel
import domain.models.movie.MovieReviewsItemUIModel
import domain.models.movie.MovieReviewsUIModel
import domain.models.movie.MovieVideoType
import domain.models.movie.MovieVideoUIModel
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

fun MovieDetailUIModel.toEntity(): MovieEntity {
    return MovieEntity(
        movieId = id ?: 0,
        movieTitle = title.orEmpty(),
        movieOverview = overview.orEmpty(),
        moviePoster = imageUrl.orEmpty()
    )
}

fun MovieVideoItem.toUIModel(): MovieVideoUIModel {
    val videoType =
        if (this.site.equals("YouTube", true)) MovieVideoType.YOUTUBE else MovieVideoType.OTHER
    return MovieVideoUIModel(
        videoUrl = this.key,
        videoType = videoType
    )
}