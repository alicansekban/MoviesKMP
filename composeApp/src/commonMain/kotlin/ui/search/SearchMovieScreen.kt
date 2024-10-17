package ui.search

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import components.button.ListResetButton
import components.dialog.LoadingDialog
import components.movie.MovieListItem
import components.top_bar.CustomTopBar
import kotlinx.coroutines.launch
import movieskmp.composeapp.generated.resources.Res
import movieskmp.composeapp.generated.resources.search_placeholder
import movieskmp.composeapp.generated.resources.search_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun SearchMovieScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchMovieViewModel = koinViewModel(),
    openMovieDetailScreen: (id: Int) -> Unit
) {

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val queryFlow by viewModel.queryFlow.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()
    val shouldFetchNextPage by remember {
        derivedStateOf {
            val lastVisibleIndex = gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            lastVisibleIndex != null &&
                    lastVisibleIndex >= uiState.uiModel.movies.size - 10 &&
                    uiState.uiModel.canLoadMore
        }
    }
    val isScrollButtonVisible by remember {
        derivedStateOf { gridState.firstVisibleItemIndex > 0 }
    }
    LaunchedEffect(shouldFetchNextPage) {
        if (shouldFetchNextPage) {
            viewModel.updateUiEvents(
                event = SearchMovieEvents.OnNextPage(
                    uiState.uiModel.page.plus(
                        1
                    )
                )
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {


        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomTopBar(
                title = stringResource(Res.string.search_title),
                shouldShowBack = false

            )
            OutlinedTextField(
                value = queryFlow,
                onValueChange = {
                    viewModel.updateUiEvents(SearchMovieEvents.OnSearchQueryChange(it))
                                },
                label = {
                    Text(
                        text = stringResource(Res.string.search_placeholder)
                    )
                        },
                maxLines = 1,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        viewModel.updateUiEvents(SearchMovieEvents.OnSearch)
                    }
                ),
                shape = RoundedCornerShape(10.dp)
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
                    if (uiState.isPaginating) {
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