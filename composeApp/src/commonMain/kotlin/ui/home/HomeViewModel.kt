package ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.interactors.movie.HomeInteractor
import domain.models.BaseUIModel
import domain.models.movie.MovieUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val interactor: HomeInteractor
) : ViewModel() {
    private val _upComingMovies =
        MutableStateFlow<BaseUIModel<List<MovieUIModel>>>(BaseUIModel.Empty)
    val upComingMovies = _upComingMovies.onStart {
        getUpComingMovies()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(10000L), BaseUIModel.Empty)
    private val _nowPlayingMovies =
        MutableStateFlow<BaseUIModel<List<MovieUIModel>>>(BaseUIModel.Empty)
    val nowPlayingMovies = _nowPlayingMovies.onStart {
        getNowPlayingMovies()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(10000L), BaseUIModel.Empty)
    private val _popularMovies =
        MutableStateFlow<BaseUIModel<List<MovieUIModel>>>(BaseUIModel.Empty)
    val popularMovies = _popularMovies.onStart {
        getPopularMovies()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(10000L), BaseUIModel.Empty)
    private val _topRatedMovies =
        MutableStateFlow<BaseUIModel<List<MovieUIModel>>>(BaseUIModel.Empty)
    val topRatedMovies = _topRatedMovies.onStart {
        getTopRatedMovies()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(10000L), BaseUIModel.Empty)

    private val _discoverMovies =
        MutableStateFlow<BaseUIModel<List<MovieUIModel>>>(BaseUIModel.Empty)
    val discoverMovies = _discoverMovies.onStart {
        getDiscoverMovies()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(10000L), BaseUIModel.Empty)

    private fun getDiscoverMovies() {
        viewModelScope.launch {
            interactor.getDiscoverMovies(1).collect {
                _discoverMovies.value = it
            }
        }
    }

    private fun getUpComingMovies() {
        viewModelScope.launch {
            interactor.getUpComingMovies(1).collect {
                _upComingMovies.value = it
            }
        }
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            interactor.getNowPlayingMovies(1).collect {
                _nowPlayingMovies.value = it
            }
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            interactor.getPopularMovies(1).collect {
                _popularMovies.value = it
            }
        }
    }

    private fun getTopRatedMovies() {
        viewModelScope.launch {
            interactor.getTopRatedMovies(1).collect {
                _topRatedMovies.value = it
            }
        }
    }
}