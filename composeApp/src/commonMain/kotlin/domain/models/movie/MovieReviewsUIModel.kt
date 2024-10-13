package domain.models.movie


data class MovieReviewsUIModel(
    val page: Int = 1,
    val reviews: List<MovieReviewsItemUIModel> = emptyList(),
    val totalPages: Int = 0,
    val totalResults: Int = 0,
    val canLoadMore: Boolean = true
)
data class MovieReviewsItemUIModel(
    val id: String? = null,
    val author: String? = null,
    val content: String? = null,
    val url: String? = null
)
