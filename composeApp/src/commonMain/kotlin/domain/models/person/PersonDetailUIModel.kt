package domain.models.person

data class PersonDetailUIModel(
    val id: Int = 0,
    val name: String? = "",
    val biography: String? = "",
    val birthday: String? = "",
    val placeOfBirth: String? = "",
    val profilePath: String? = ""
)
