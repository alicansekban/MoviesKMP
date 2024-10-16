package ui.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.interactors.person.PersonIndicator
import domain.models.BaseUIModel
import domain.models.movie.MovieListUIModel
import domain.models.person.PersonDetailUIModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PersonDetailViewModel(
    private val interactor: PersonIndicator
) : ViewModel() {

    private val _personDetail = MutableStateFlow<BaseUIModel<PersonDetailUIModel>>(BaseUIModel.Empty)
    val personDetail = _personDetail.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(10000L),
        BaseUIModel.Empty
    )

    private val _personImages = MutableStateFlow<BaseUIModel<List<String>>>(BaseUIModel.Empty)
    val personImages = _personImages.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(10000L),
        BaseUIModel.Empty
    )

    fun callApiCalls(id: Int) {
        viewModelScope.launch {
            // Tüm async çağrılar paralel olarak başlatılır ve tümünün bitmesi beklenir
            val tasks = listOf(
                async { getPersonDetail(id) },
                async { getPersonImages(id) },
            )

            // Tüm async işlemler tamamlanana kadar bekliyoruz
            tasks.awaitAll()
        }
    }

    private fun getPersonDetail(id: Int) {
        viewModelScope.launch {
            interactor.getPersonDetail(id).collect {
                _personDetail.value = it
            }
        }
    }

    private fun getPersonImages(id: Int) {
        viewModelScope.launch {
            interactor.getPersonImages(id).collect {
                _personImages.value = it
            }
        }
    }
}