package ui.movie_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import components.imageView.CustomImageView
import domain.models.MovieCreditsUIModel

@Composable
fun MovieDetailCast(
    modifier: Modifier = Modifier,
    cast: List<MovieCreditsUIModel>
) {

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(
            items = cast,
            key = { it.id ?: 0 },
        ) {
            Column(
                modifier = Modifier.width(100.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                it.imageUrl?.let { it1 ->
                    CustomImageView(
                        modifier = Modifier.size(100.dp)
                            .shadow(shape = CircleShape, elevation = 5.dp),
                        imageUrl = it1,
                        onClick = {}
                    )
                }
                it.characterName?.let {
                    Text(it, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }

        }
    }

}