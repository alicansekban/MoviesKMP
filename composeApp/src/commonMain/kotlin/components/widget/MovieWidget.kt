package components.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import components.imageView.CustomImageView

@Composable
fun CustomWidget(
    model: MovieWidgetComponentModel,
    openListScreen: () -> Unit = {},
    openMovieDetailScreen: (id: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable { openListScreen() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            model.title?.let {
                Text(
                    modifier = Modifier.weight(1f),
                    text = it,
                )
            }
            Text(
                text = "View all",
                textDecoration = TextDecoration.Underline,
            )
        }
        LazyRow(Modifier.fillMaxWidth(), contentPadding = PaddingValues(start = 16.dp)) {
            items(model.items, key = { it.id ?: 0 }) {
                WidgetItem(item = it, openMovieDetailScreen = openMovieDetailScreen)
            }
        }
    }
}

@Composable
fun WidgetItem(
    item: WidgetMovieModel,
    openMovieDetailScreen: (id: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .clickable {
                item.id?.let { openMovieDetailScreen(it) }
            }
            .padding(8.dp)
            .width(150.dp)
            .height(300.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        CustomImageView(
            imageUrl = item.imageUrl ?: "",
            modifier = Modifier
                .clip(shape = RoundedCornerShape(10.dp))
                .height(250.dp)
                .width(150.dp)
        )

        item.title?.let {
            Text(
                text = it,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}