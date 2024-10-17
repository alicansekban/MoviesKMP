package ui.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.interactors.movie.MovieDetailInteractor
import domain.models.BaseUIModel
import domain.models.movie.MovieVideoUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VideoScreenViewModel(
    private val interactor: MovieDetailInteractor
): ViewModel() {

    private val _video = MutableStateFlow<BaseUIModel<MovieVideoUIModel?>>(BaseUIModel.Empty)
    val video = _video.stateIn(viewModelScope, SharingStarted.Eagerly,BaseUIModel.Empty)

    fun getVideos(movieId : Int) {
        viewModelScope.launch {
            interactor.getMovieVideos(movieId).collect{state ->
                _video.emit(state)
            }
        }
    }

}
