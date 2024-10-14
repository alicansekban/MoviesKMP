package data.repository

import data.remote.PersonApiService
import data.response.person.PersonDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import utils.ResultWrapper

class PersonRepository(
    private val personApiService: PersonApiService
) {
    fun getPersonDetail(id: Int) = flow {
        emit(personApiService.getPersonDetail(id))
    }.flowOn(Dispatchers.IO)

    fun getPersonImages(id: Int) = flow {
        emit(personApiService.getPersonImages(id))
    }.flowOn(Dispatchers.IO)

}