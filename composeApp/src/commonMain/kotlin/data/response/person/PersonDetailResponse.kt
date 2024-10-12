package data.response.person

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PersonDetailResponse(

	@SerialName("also_known_as")
	val also_known_as: List<String>? = null,

	@SerialName("birthday")
	val birthday: String? = null,

	@SerialName("gender")
	val gender: Int? = null,

	@SerialName("imdb_id")
	val imdb_id: String? = null,

	@SerialName("known_for_department")
	val known_for_department: String? = null,

	@SerialName("profile_path")
	val profile_path: String? = null,

	@SerialName("biography")
	val biography: String? = null,

	@SerialName("deathday")
	val deathday: String? = null,

	@SerialName("place_of_birth")
	val place_of_birth: String? = null,

	@SerialName("popularity")
	val popularity: Double? = null,

	@SerialName("name")
	val name: String? = null,

	@SerialName("id")
	val id: Int? = null,

	@SerialName("adult")
	val adult: Boolean? = null,
)
