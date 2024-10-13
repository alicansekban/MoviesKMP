package ui.movie_detail

import androidx.compose.animation.AnimatedVisibility
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
import ui.movie_detail.components.MovieDetailReview
import ui.movie_detail.components.MovieDetails

@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailViewModel,
    onBackClick: () -> Unit,
    openMovieDetailScreen: (id: Int) -> Unit,
    movieId: Int,
    openReviewScreen: (id: Int) -> Unit,
    openPersonDetailScreen: (id: Int) -> Unit
) {

    val movieDetailState by viewModel.movieDetail.collectAsStateWithLifecycle()
    val imagesState by viewModel.images.collectAsStateWithLifecycle()
    val creditsState by viewModel.credits.collectAsStateWithLifecycle()
    val reviewsState by viewModel.reviews.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        CustomTopBar(
            title = "Movie Detail",
            onBackClick = onBackClick
        )
        AnimatedVisibility(uiState.showUi) {


            Column(
                modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
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
                        MovieDetailCast(cast = credits, onCastClick = openPersonDetailScreen)
                    }
                }
                AnimatedVisibility(uiState.similarMovies.movies.isNotEmpty()) {
                    val widgetModel = MovieWidgetComponentModel(
                        title = "Similar Movies",
                        items = uiState.similarMovies.movies.map { it.toWidgetModel() }
                    )
                    CustomWidget(
                        model = widgetModel,
                        openListScreen = {},
                        openMovieDetailScreen = openMovieDetailScreen,
                        getNextPage = {
                            if (uiState.similarMovies.canLoadMore) {
                                viewModel.getSimilarMovies(
                                    id = movieId,
                                    page = uiState.similarMovies.page.plus(1),
                                    currentModel = uiState.similarMovies
                                )
                            }
                        },
                        showSeeAll = false
                    )
                }

                AnimatedVisibility(uiState.recommendations.movies.isNotEmpty()) {
                    val widgetModel = MovieWidgetComponentModel(
                        title = "Recommendations",
                        items = uiState.recommendations.movies.map { it.toWidgetModel() }
                    )
                    CustomWidget(
                        model = widgetModel,
                        openListScreen = {},
                        openMovieDetailScreen = openMovieDetailScreen,
                        getNextPage = {
                            if (uiState.recommendations.canLoadMore) {
                                viewModel.getRecommendations(
                                    id = movieId,
                                    page = uiState.recommendations.page.plus(1),
                                    currentModel = uiState.recommendations
                                )
                            }
                        },
                        showSeeAll = false
                    )
                }
                when (reviewsState) {
                    BaseUIModel.Empty -> {}
                    is BaseUIModel.Error -> {}
                    BaseUIModel.Loading -> {}
                    is BaseUIModel.Success -> {
                        val review = (reviewsState as BaseUIModel.Success).data
                        MovieDetailReview(
                            reviews = review.reviews,
                            onSeeAllClick = {
                                openReviewScreen.invoke(movieId)
                            }
                        )
                    }
                }
            }
        }

    }
}