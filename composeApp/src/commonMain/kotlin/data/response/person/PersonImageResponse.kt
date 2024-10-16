package data.response.person

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PersonImageResponse(

	@SerialName("profiles")
	val profiles: List<ProfilesItem>? = null,

	@SerialName("id")
	val id: Int? = null
)

@Serializable
data class ProfilesItem(

	@SerialName("aspect_ratio")
	val aspect_ratio: Double? = null,

	@SerialName("file_path")
	val file_path: String? = null,

	@SerialName("vote_average")
	val vote_average: Double? = null,

	@SerialName("width")
	val width: Int? = null,

	@SerialName("iso_639_1")
	val iso_639_1: Double? = null,

	@SerialName("vote_count")
	val vote_count: Int? = null,

	@SerialName("height")
	val height: Int? = null
)
