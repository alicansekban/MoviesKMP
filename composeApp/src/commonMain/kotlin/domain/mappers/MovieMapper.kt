package domain.mappers

import data.response.movie.BaseMoviesResponse
import data.response.movie.MovieResponse
import domain.models.MovieListUIModel
import domain.models.MovieType
import domain.models.MovieUIModel
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