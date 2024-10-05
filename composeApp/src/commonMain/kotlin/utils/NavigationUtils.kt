package utils

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