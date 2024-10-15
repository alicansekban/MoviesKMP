package ui.movie_reviews

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import components.button.ListResetButton
import components.top_bar.CustomTopBar
import kotlinx.coroutines.launch
import movieskmp.composeapp.generated.resources.Res
import movieskmp.composeapp.generated.resources.movie_reviews
import org.jetbrains.compose.resources.stringResource

@Composable
fun MovieReviewsScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: MovieReviewsViewModel,
    movieID: Int
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val shouldFetchNextPage by remember {
        derivedStateOf {
            val lastVisibleIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            lastVisibleIndex != null &&
                    lastVisibleIndex >= uiState.review.reviews.size - 10 &&
                    uiState.review.canLoadMore
        }
    }
    LaunchedEffect(shouldFetchNextPage) {
        if (shouldFetchNextPage) {
            viewModel.getReviews(
                id = movieID,
                page = uiState.review.page.plus(1)
            )
        }
    }
    val isScrollButtonVisible by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }
    val scope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            CustomTopBar(
                title = stringResource(Res.string.movie_reviews),
                onBackClick = onBackClick
            )
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(
                    uiState.review.reviews,
                    key = { it.id ?: 0 }
                ) {
                    Column(
                        modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        it.author?.let { it1 ->
                            Text(
                                text = "Author: $it1",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        it.content?.let { it1 ->
                            Text(
                                it1,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                    }
                }
            }

        }
        AnimatedVisibility(isScrollButtonVisible,modifier = Modifier.align(Alignment.BottomEnd)) {
            ListResetButton {
                scope.launch {
                    listState.animateScrollToItem(0)
                }
            }
        }
    }


}