package data.response.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieVideoResponse(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("results")
    val results: List<MovieVideoItem>? = emptyList(),
)
@Serializable
data class MovieVideoItem(
    @SerialName("type")
    val type: String? = null,
    @SerialName("size")
    val size: Int? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("official")
    val official : String? = null,
    @SerialName("key")
    val key: String? = null,
    @SerialName("site")
    val site: String? = null
)