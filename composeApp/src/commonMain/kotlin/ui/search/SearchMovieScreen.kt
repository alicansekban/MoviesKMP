package ui.search

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import components.imageView.CustomImageView
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun SearchMovieScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchMovieViewModel = koinViewModel(),
    openMovieDetailScreen: (id: Int) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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
            viewModel.updateUiEvents(
                event = SearchMovieEvents.OnNextPage(
                    uiState.uiModel.page.plus(
                        1
                    )
                )
            )
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        OutlinedTextField(
            value = uiState.query,
            onValueChange = {
                viewModel.updateUiEvents(SearchMovieEvents.OnSearchQueryChange(it))
            },
            label = {
                Text(
                    text = "Search"
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
            )
        )
        LazyVerticalGrid(
            modifier = modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            state = gridState,
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.uiModel.movies, key = { it.id ?: 0 }) {
                it.imageUrl?.let { it1 ->
                    CustomImageView(
                        imageUrl = it1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        onClick = {
                            openMovieDetailScreen.invoke(it.id ?: 0)
                        }
                    )
                }
            }
        }

    }

}