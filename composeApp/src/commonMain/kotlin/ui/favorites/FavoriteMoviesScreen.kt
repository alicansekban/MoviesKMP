package ui.favorites

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import components.button.ListResetButton
import components.imageView.CustomImageView
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
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment =
                        Alignment.CenterHorizontally
                    ) {
                        Box(Modifier.fillMaxWidth().height(250.dp)) {
                            movie.imageUrl?.let { image ->
                                CustomImageView(
                                    imageUrl = image,
                                    modifier = Modifier.fillMaxSize(),
                                    onClick = {
                                        openMovieDetailScreen.invoke(movie.id ?: 0)
                                    }
                                )
                            }
                            val icon =
                                if (movie.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                            Icon(
                                imageVector = icon,
                                contentDescription = "",
                                Modifier.clickable {
                                    viewModel.onFavoriteIconClicked(movie.id ?: 0)
                                }.align(Alignment.TopEnd).padding(8.dp),
                                tint = Color.Red
                            )
                        }

                        movie.title?.let { title ->
                            androidx.compose.material.Text(title)
                        }
                    }
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