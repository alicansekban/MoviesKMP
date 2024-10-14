package domain.mappers.movie

import data.local.entity.MovieEntity
import data.response.movie.BaseMoviesResponse
import data.response.movie.MovieResponse
import domain.models.movie.MovieListUIModel
import domain.models.movie.MovieType
import domain.models.movie.MovieUIModel
import utils.Constants

fun MovieResponse.toUIModel(): MovieUIModel {
    return MovieUIModel(
        id = id,
        title = title,
        imageUrl = Constants.BASE_POSTER_URL + this.poster_path
    )
}

fun BaseMoviesResponse.toUIModel(
    currentModel: MovieListUIModel,
    movieType: MovieType
): MovieListUIModel {
    val newMovies = this.results?.map { it.toUIModel() } ?: emptyList()
    val updatedMovies = currentModel.movies.plus(newMovies).distinctBy { it.id }
    return MovieListUIModel(
        page = page ?: 0,
        movies = updatedMovies,
        totalPages = total_pages ?: 0,
        totalResults = total_results ?: 0,
        canLoadMore = page != total_pages,
        movieType = movieType
    )
}

fun MovieUIModel.toEntity(): MovieEntity {
    return MovieEntity(
        movieId = id ?: 0,
        movieTitle = title ?: "",
        moviePoster = imageUrl ?: "",
        movieOverview = overview ?: ""
    )
}

fun MovieEntity.toUIModel(): MovieUIModel {
    return MovieUIModel(
        id = movieId,
        title = movieTitle,
        imageUrl = moviePoster,
        overview = movieOverview,
        isFavorite = true
    )
}
