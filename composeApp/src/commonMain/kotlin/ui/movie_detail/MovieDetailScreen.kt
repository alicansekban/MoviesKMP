package ui.movie_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import components.top_bar.CustomTopBar

@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailViewModel,
    onBackClick: () -> Unit
) {


    Column(
        modifier.fillMaxSize()
    ) {

        CustomTopBar(
            title = "Movie Detail",
            onBackClick = onBackClick
        )

    }
}