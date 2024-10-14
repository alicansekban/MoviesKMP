package ui.movie_detail.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import components.imageView.CustomImageView

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieDetailPager(
    modifier: Modifier = Modifier,
    images: List<String>,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {}
) {

    val pagerState = rememberPagerState(pageCount = { images.size })

    Box(modifier = modifier) {
        HorizontalPager(pagerState) { index ->
            val currentMovie = images[index]
            CustomImageView(
                imageUrl = currentMovie,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.40f),
                onClick = {}
            )

        }
        val icon =
            if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
        Icon(
            imageVector = icon,
            contentDescription = "",
            Modifier.clickable {
                onFavoriteClick.invoke()
            }.align(Alignment.TopEnd).padding(8.dp),
            tint = Color.Red
        )

    }

}