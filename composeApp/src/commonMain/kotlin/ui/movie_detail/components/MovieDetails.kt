package ui.movie_detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import domain.models.movie.MovieDetailUIModel

@Composable
fun MovieDetails(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetailUIModel,
    openVideosScreen : () -> Unit
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
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

            movieDetail.voteAvg?.let {
                Text(it)

            }
            Text(text = "Watch Trailer", modifier = Modifier.clickable {
                openVideosScreen.invoke()
            },
                textDecoration = TextDecoration.Underline
            )
        }


    }
}