package utils

import domain.models.movie.MovieVideoType
import kotlinx.serialization.Serializable


@Serializable
object HomeHost

@Serializable
object HomeRoute

@Serializable
data class MovieDetailRoute(val movieId: Int)

@Serializable
data class MovieListRoute(val type: String)

@Serializable
object FavoritesHost

@Serializable
object FavoritesRoute

@Serializable
object SearchHost

@Serializable
object SearchRoute

@Serializable
data class MovieReviewsRoute(val movieId: Int)

@Serializable
data class PersonDetailRoute(val personId: Int)

@Serializable
data class VideoScreenRoute(val movieId : Int)