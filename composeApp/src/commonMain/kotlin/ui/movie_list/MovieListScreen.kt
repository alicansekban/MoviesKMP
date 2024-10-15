package ui.movie_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import components.dialog.LoadingDialog
import components.movie.MovieListItem
import components.top_bar.CustomTopBar
import kotlinx.coroutines.launch
import movieskmp.composeapp.generated.resources.Res
import movieskmp.composeapp.generated.resources.movie_list
import org.jetbrains.compose.resources.stringResource

@Composable
fun MovieListScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieListViewModel,
    openMovieDetailScreen: (id: Int) -> Unit,
    onBackClick: () -> Unit
) {

    val uiState by viewModel.movies.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()
    val shouldFetchNextPage by remember {
        derivedStateOf {
            val lastVisibleIndex = gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            lastVisibleIndex != null &&
                    lastVisibleIndex >= uiState.uiModel.movies.size - 10 &&
                    uiState.uiModel.canLoadMore
        }
    }
    LaunchedEffect(shouldFetchNextPage) {
        if (shouldFetchNextPage) {
            viewModel.updateEvents(event = MovieListUIEvents.GetNextPage)
        }
    }
    val isScrollButtonVisible by remember {
        derivedStateOf { gridState.firstVisibleItemIndex > 0 }
    }
    val scope = rememberCoroutineScope()
    if (uiState.uiModel.page == 1 && uiState.isLoading) {
        LoadingDialog()
    }
    Box(modifier = Modifier.fillMaxSize()) {


        Column(modifier.fillMaxSize()) {
            CustomTopBar(
                title = stringResource(Res.string.movie_list),
                onBackClick = onBackClick
            )
            LazyVerticalGrid(
                modifier = modifier.fillMaxSize(),
                columns = GridCells.Fixed(2),
                state = gridState,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.uiModel.movies, key = { it.id ?: 0 }) { movie ->
                    MovieListItem(
                        movie = movie,
                        onFavoriteClick = {
                            viewModel.onFavoriteClicked(movie)
                        },
                        onItemClick = {
                            openMovieDetailScreen.invoke(movie.id ?: 0)
                        }
                    )
                }
                item(span = {
                    GridItemSpan(maxLineSpan)
                }
                ) {
                    if (uiState.uiModel.page > 1 && uiState.isLoading) {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = Color.Red
                            )
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