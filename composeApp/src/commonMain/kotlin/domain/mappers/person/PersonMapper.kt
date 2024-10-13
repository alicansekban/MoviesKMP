package domain.mappers.person

import data.response.person.PersonDetailResponse
import domain.models.person.PersonDetailUIModel
import utils.Constants

fun PersonDetailResponse.toUIModel() : PersonDetailUIModel {
    return PersonDetailUIModel(
        id = id ?: 0,
        name = name.orEmpty(),
        biography = biography.orEmpty(),
        birthday = birthday.orEmpty(),
        placeOfBirth = place_of_birth.orEmpty(),
        profilePath = Constants.BASE_POSTER_URL + profile_path
    )
}