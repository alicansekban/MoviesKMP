package domain.models.movie

data class MovieVideoUIModel(
    val videoId: String? = null,
    val videoUrl: String? = null,
    val videoType: MovieVideoType = MovieVideoType.YOUTUBE
)

enum class MovieVideoType {
    YOUTUBE, OTHER
}