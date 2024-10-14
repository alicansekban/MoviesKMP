package ui.favorites

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import components.button.ListResetButton
import components.movie.MovieListItem
import components.top_bar.CustomTopBar
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavoriteMoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteMoviesViewModel = koinViewModel(),
    openMovieDetailScreen: (id: Int) -> Unit
) {
    val gridState = rememberLazyGridState()

    val favorites by viewModel.favorites.collectAsStateWithLifecycle()

    val isScrollButtonVisible by remember {
        derivedStateOf { gridState.firstVisibleItemIndex > 0 }
    }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {


        Column(modifier.fillMaxSize()) {
            CustomTopBar(
                title = "Favorite Movies",
                shouldShowBack = false
            )
            LazyVerticalGrid(
                modifier = modifier.fillMaxSize(),
                columns = GridCells.Fixed(2),
                state = gridState,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favorites, key = { it.id ?: 0 }) { movie ->
                    MovieListItem(
                        movie = movie,
                        onFavoriteClick = {
                            viewModel.onFavoriteIconClicked(movie.id ?: 0)
                        },
                        onItemClick = {
                            openMovieDetailScreen.invoke(movie.id ?: 0)
                        }
                    )
                }
            }
        }
        AnimatedVisibility(
            isScrollButtonVisible, modifier = Modifier
                .align(Alignment.BottomEnd).padding(end = 12.dp, bottom = 24.dp)
        ) {
            ListResetButton {
                scope.launch {
                    gridState.animateScrollToItem(0)
                }
            }
        }
    }

}