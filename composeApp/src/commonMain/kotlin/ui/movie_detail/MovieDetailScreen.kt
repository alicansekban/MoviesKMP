package ui.movie_detail

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
import components.top_bar.CustomTopBar
import components.widget.CustomWidget
import components.widget.MovieWidgetComponentModel
import components.widget.toWidgetModel
import domain.models.BaseUIModel
import ui.movie_detail.components.MovieDetailCast
import ui.movie_detail.components.MovieDetailPager
import ui.movie_detail.components.MovieDetails

@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailViewModel,
    onBackClick: () -> Unit
) {

    val movieDetailState by viewModel.movieDetail.collectAsStateWithLifecycle()
    val imagesState by viewModel.images.collectAsStateWithLifecycle()
    val creditsState by viewModel.credits.collectAsStateWithLifecycle()
    val reviewsState by viewModel.reviews.collectAsStateWithLifecycle()
    val recommendationsState by viewModel.recommendations.collectAsStateWithLifecycle()
    val similarMoviesState by viewModel.similarMovies.collectAsStateWithLifecycle()

    Column(
        modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        CustomTopBar(
            title = "Movie Detail",
            onBackClick = onBackClick
        )
        when (imagesState) {
            BaseUIModel.Empty -> {}
            is BaseUIModel.Error -> {}
            BaseUIModel.Loading -> {}
            is BaseUIModel.Success -> {
                val images = (imagesState as BaseUIModel.Success).data
                MovieDetailPager(images = images)
            }
        }
        when (movieDetailState) {
            BaseUIModel.Empty -> {}
            is BaseUIModel.Error -> {}
            BaseUIModel.Loading -> {}
            is BaseUIModel.Success -> {
                val movieDetail = (movieDetailState as BaseUIModel.Success).data
                MovieDetails(movieDetail = movieDetail)
            }
        }

        when (creditsState) {
            BaseUIModel.Empty -> {}
            is BaseUIModel.Error -> {}
            BaseUIModel.Loading -> {}
            is BaseUIModel.Success -> {
                val credits = (creditsState as BaseUIModel.Success).data
                MovieDetailCast(cast = credits)
            }
        }

        when (recommendationsState) {
            BaseUIModel.Empty -> {}
            is BaseUIModel.Error -> {}
            BaseUIModel.Loading -> {}
            is BaseUIModel.Success -> {
                val recommendations = (recommendationsState as BaseUIModel.Success).data
                val widgetModel = MovieWidgetComponentModel(
                    title = "Recommendations",
                    items = recommendations.movies.map { it.toWidgetModel() }
                )
                CustomWidget(
                    model = widgetModel,
                    openListScreen = {},
                    openMovieDetailScreen = {}
                )
            }
        }

        when (similarMoviesState) {
            BaseUIModel.Empty -> {}
            is BaseUIModel.Error -> {}
            BaseUIModel.Loading -> {}
            is BaseUIModel.Success -> {
                val similar = (similarMoviesState as BaseUIModel.Success).data
                val widgetModel = MovieWidgetComponentModel(
                    title = "Similar Movies",
                    items = similar.movies.map { it.toWidgetModel() }
                )
                CustomWidget(
                    model = widgetModel,
                    openListScreen = {},
                    openMovieDetailScreen = {}
                )
            }
        }


    }
}