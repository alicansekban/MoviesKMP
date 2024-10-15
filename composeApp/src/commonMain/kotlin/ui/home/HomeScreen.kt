@file:OptIn(ExperimentalFoundationApi::class)

package ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import components.imageView.CustomImageView
import components.widget.CustomWidget
import components.widget.MovieWidgetComponentModel
import components.widget.toWidgetModel
import domain.models.BaseUIModel
import domain.models.movie.MovieType
import domain.models.movie.MovieUIModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    openListScreen: (type: String) -> Unit,
    openMovieDetailScreen: (id: Int) -> Unit
) {

    val upComingMovies by viewModel.upComingMovies.collectAsStateWithLifecycle()
    val nowPlayingMovies by viewModel.nowPlayingMovies.collectAsStateWithLifecycle()
    val popularMovies by viewModel.popularMovies.collectAsStateWithLifecycle()
    val topRatedMovies by viewModel.topRatedMovies.collectAsStateWithLifecycle()
    val discoverMovies by viewModel.discoverMovies.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        when (discoverMovies) {
            BaseUIModel.Empty -> {}
            is BaseUIModel.Error -> {}
            BaseUIModel.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.Red
                    )
                }
            }
            is BaseUIModel.Success -> {
                val movies = (discoverMovies as BaseUIModel.Success).data
                HomeMoviePager(
                    movies = movies,
                    openMovieDetailScreen = openMovieDetailScreen
                )
            }
        }
        when (upComingMovies) {
            BaseUIModel.Empty -> {}
            is BaseUIModel.Error -> {}
            BaseUIModel.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.Red
                    )
                }
            }
            is BaseUIModel.Success -> {
                val movies = (upComingMovies as BaseUIModel.Success).data
                val widgetMovies = movies.map { it.toWidgetModel() }
                val widgetModel = MovieWidgetComponentModel(
                    title = "Up Coming Movies",
                    items = widgetMovies
                )

                CustomWidget(model = widgetModel, openListScreen = {
                    openListScreen.invoke(MovieType.UPCOMING.type)
                }, openMovieDetailScreen = openMovieDetailScreen)
            }
        }
        when (nowPlayingMovies) {
            BaseUIModel.Empty -> {}
            is BaseUIModel.Error -> {}
            BaseUIModel.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.Red
                    )
                }
            }
            is BaseUIModel.Success -> {
                val movies = (nowPlayingMovies as BaseUIModel.Success).data
                val widgetMovies = movies.map { it.toWidgetModel() }
                val widgetModel = MovieWidgetComponentModel(
                    title = "Now Playing Movies",
                    items = widgetMovies
                )

                CustomWidget(model = widgetModel, openListScreen = {
                    openListScreen.invoke(MovieType.NOW_PLAYING.type)
                }, openMovieDetailScreen = openMovieDetailScreen)
            }
        }
        when (popularMovies) {
            BaseUIModel.Empty -> {}
            is BaseUIModel.Error -> {}
            BaseUIModel.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.Red
                    )
                }
            }
            is BaseUIModel.Success -> {
                val movies = (popularMovies as BaseUIModel.Success).data
                val widgetMovies = movies.map { it.toWidgetModel() }
                val widgetModel = MovieWidgetComponentModel(
                    title = "Popular Movies",
                    items = widgetMovies
                )

                CustomWidget(model = widgetModel, openListScreen = {
                    openListScreen.invoke(MovieType.POPULAR.type)
                }, openMovieDetailScreen = openMovieDetailScreen)
            }
        }
        when (topRatedMovies) {
            BaseUIModel.Empty -> {}
            is BaseUIModel.Error -> {}
            BaseUIModel.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.Red
                    )
                }
            }
            is BaseUIModel.Success -> {
                val movies = (topRatedMovies as BaseUIModel.Success).data
                val widgetMovies = movies.map { it.toWidgetModel() }
                val widgetModel = MovieWidgetComponentModel(
                    title = "Top Rated Movies",
                    items = widgetMovies
                )

                CustomWidget(model = widgetModel, openListScreen = {
                    openListScreen.invoke(MovieType.TOP_RATED.type)
                }, openMovieDetailScreen = openMovieDetailScreen)
            }
        }
    }
}

@Composable
fun HomeMoviePager(
    modifier: Modifier = Modifier,
    movies: List<MovieUIModel>,
    openMovieDetailScreen: (id: Int) -> Unit
) {

    val pagerState = rememberPagerState(pageCount = { movies.size })

    HorizontalPager(pagerState) { index ->
        val currentMovie = movies[index]
        currentMovie.imageUrl?.let {
            CustomImageView(
                imageUrl = it,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.40f),
                onClick = {
                    currentMovie.id?.let { it1 -> openMovieDetailScreen.invoke(it1) }
                }
            )
        }

    }

}