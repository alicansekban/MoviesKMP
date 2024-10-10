package domain.models

data class MovieListUIModel(
    val movies: List<MovieUIModel> = emptyList(),
    val page: Int = 1,
    val totalPages: Int = 1,
    val totalResults: Int = 0,
    val canLoadMore: Boolean = false,
    val movieType: MovieType = MovieType.UPCOMING
)

data class MovieUIModel(
    val id: Int? = 0,
    val title: String? = null,
    val imageUrl: String? = null,
    val overview: String? = null,
)

enum class MovieType(val type: String) {
    UPCOMING("upcoming"),
    NOW_PLAYING("now_playing"),
    TOP_RATED("top_rated"),
    POPULAR("popular"),
    SIMILAR("similar"),
    RECOMMENDATIONS("recommendations"),
    SEARCH("search")
}

