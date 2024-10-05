package ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import components.widget.CustomWidget
import components.widget.MovieWidgetComponentModel
import components.widget.toWidgetModel
import domain.models.BaseUIModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val upComingMovies by viewModel.upComingMovies.collectAsStateWithLifecycle()
    val nowPlayingMovies by viewModel.nowPlayingMovies.collectAsStateWithLifecycle()
    val popularMovies by viewModel.popularMovies.collectAsStateWithLifecycle()
    val topRatedMovies by viewModel.topRatedMovies.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        when (upComingMovies) {
            BaseUIModel.Empty -> {}
            is BaseUIModel.Error -> {}
            BaseUIModel.Loading -> {}
            is BaseUIModel.Success -> {
                val movies = (upComingMovies as BaseUIModel.Success).data
                val widgetMovies = movies.map { it.toWidgetModel() }
                val widgetModel = MovieWidgetComponentModel(
                    title = "Up Coming Movies",
                    items = widgetMovies
                )

                CustomWidget(model = widgetModel, openListScreen = {
                }, openMovieDetailScreen = {})
            }
        }
        when (nowPlayingMovies) {
            BaseUIModel.Empty -> {}
            is BaseUIModel.Error -> {}
            BaseUIModel.Loading -> {}
            is BaseUIModel.Success -> {
                val movies = (nowPlayingMovies as BaseUIModel.Success).data
                val widgetMovies = movies.map { it.toWidgetModel() }
                val widgetModel = MovieWidgetComponentModel(
                    title = "Now Playing Movies",
                    items = widgetMovies
                )

                CustomWidget(model = widgetModel, openListScreen = {
                }, openMovieDetailScreen = {})
            }
        }
        when (popularMovies) {
            BaseUIModel.Empty -> {}
            is BaseUIModel.Error -> {}
            BaseUIModel.Loading -> {}
            is BaseUIModel.Success -> {
                val movies = (popularMovies as BaseUIModel.Success).data
                val widgetMovies = movies.map { it.toWidgetModel() }
                val widgetModel = MovieWidgetComponentModel(
                    title = "Popular Movies",
                    items = widgetMovies
                )

                CustomWidget(model = widgetModel, openListScreen = {
                }, openMovieDetailScreen = {})
            }
        }
        when (topRatedMovies) {
            BaseUIModel.Empty -> {}
            is BaseUIModel.Error -> {}
            BaseUIModel.Loading -> {}
            is BaseUIModel.Success -> {
                val movies = (topRatedMovies as BaseUIModel.Success).data
                val widgetMovies = movies.map { it.toWidgetModel() }
                val widgetModel = MovieWidgetComponentModel(
                    title = "Top Rated Movies",
                    items = widgetMovies
                )

                CustomWidget(model = widgetModel, openListScreen = {
                }, openMovieDetailScreen = {})
            }
        }
    }
}