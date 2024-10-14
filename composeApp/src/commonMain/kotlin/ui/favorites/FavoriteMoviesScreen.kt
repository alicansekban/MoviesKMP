package ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavoriteMoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteMoviesViewModel = koinViewModel(),
) {

    val favorites by viewModel.favorites.collectAsStateWithLifecycle()


    Column(modifier = modifier.fillMaxSize()) {

        favorites.forEach {
            Text(text = it.movieTitle)
        }
    }

}