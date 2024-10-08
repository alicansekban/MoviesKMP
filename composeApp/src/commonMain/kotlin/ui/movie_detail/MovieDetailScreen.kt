package ui.movie_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import components.top_bar.CustomTopBar
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

    Column(
        modifier.fillMaxSize(),
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


    }
}