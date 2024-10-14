package components.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import components.imageView.CustomImageView
import domain.models.movie.MovieUIModel

@Composable
fun MovieListItem(
    modifier: Modifier = Modifier,
    movie: MovieUIModel,
    onFavoriteClick: () -> Unit,
    onItemClick: () -> Unit
) {

    Column(
        modifier = modifier.fillMaxWidth(),
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
                        onItemClick.invoke()
                    }
                )
            }
            val icon =
                if (movie.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
            Icon(
                imageVector = icon,
                contentDescription = "",
                Modifier.clickable {
                    onFavoriteClick.invoke()
                }.align(Alignment.TopEnd).padding(8.dp),
                tint = Color.Red
            )
        }

        movie.title?.let { title ->
            Text(title)
        }
    }
}