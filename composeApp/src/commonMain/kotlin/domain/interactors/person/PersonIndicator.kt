package domain.interactors.person

import data.repository.PersonRepository
import domain.mappers.movie.toUIModel
import domain.mappers.person.toUIModel
import domain.models.BaseUIModel
import domain.models.person.PersonDetailUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import utils.Constants
import utils.ResultWrapper

class PersonIndicator(
    private val repository: PersonRepository
) {

    fun getPersonDetail(id: Int): Flow<BaseUIModel<PersonDetailUIModel>> {
        return flow {
            emit(BaseUIModel.Loading)
            repository.getPersonDetail(id).collect { state ->
                when (state) {
                    is ResultWrapper.GenericError -> emit(BaseUIModel.Error(state.error ?: "Error"))
                    ResultWrapper.Loading -> emit(BaseUIModel.Loading)
                    ResultWrapper.NetworkError -> emit(BaseUIModel.Error("Network Error"))
                    is ResultWrapper.Success -> {
                        val uiModel = state.value.toUIModel()
                        emit(BaseUIModel.Success(uiModel))
                    }
                }
            }
        }
    }

    fun getPersonImages(id: Int): Flow<BaseUIModel<List<String>>> {
        return flow {
            emit(BaseUIModel.Loading)
            repository.getPersonImages(id).collect { state ->
                when (state) {
                    is ResultWrapper.GenericError -> emit(BaseUIModel.Error(state.error ?: "Error"))
                    ResultWrapper.Loading -> emit(BaseUIModel.Loading)
                    ResultWrapper.NetworkError -> emit(BaseUIModel.Error("Network Error"))
                    is ResultWrapper.Success -> {
                        val uiModel = state.value.profiles?.map { Constants.BASE_POSTER_URL + it.file_path } ?: emptyList()
                        emit(BaseUIModel.Success(uiModel))
                    }
                }
            }
        }

    }
}