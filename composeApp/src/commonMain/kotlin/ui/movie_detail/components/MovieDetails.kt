package ui.movie_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.models.MovieDetailUIModel

@Composable
fun MovieDetails(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetailUIModel
) {

    Column(modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            movieDetail.title?.let {
                Text(it)
            }
            movieDetail.duration?.let {
                Text(it)
            }
        }
        movieDetail.overview?.let {
            Text(it)
        }
        movieDetail.voteAvg?.let {
            Text(it)

        }


    }
}