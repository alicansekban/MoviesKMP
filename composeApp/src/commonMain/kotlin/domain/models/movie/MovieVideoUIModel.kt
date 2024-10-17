package domain.models.movie

import kotlinx.serialization.Serializable

data class MovieVideoUIModel(
    val videoUrl: String? = null,
    val videoType: MovieVideoType = MovieVideoType.YOUTUBE
)

@Serializable
enum class MovieVideoType {
    YOUTUBE, OTHER
}