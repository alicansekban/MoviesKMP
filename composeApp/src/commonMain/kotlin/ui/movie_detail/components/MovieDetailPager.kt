package ui.movie_detail.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import components.imageView.CustomImageView

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieDetailPager(
    modifier: Modifier = Modifier,
    images: List<String>,
) {

    val pagerState = rememberPagerState(pageCount = { images.size })

    HorizontalPager(pagerState) { index ->
        val currentMovie = images[index]
        CustomImageView(
            imageUrl = currentMovie,
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.40f),
            onClick = {}
        )

    }
}