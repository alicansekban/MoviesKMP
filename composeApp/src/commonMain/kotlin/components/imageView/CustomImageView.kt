package components.imageView

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun CustomImageView(
    imageUrl: String,
    modifier: Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    onClick: () -> Unit = {}
) {
    AsyncImage(
        modifier = modifier.clip(RoundedCornerShape(5.dp)).clickable { onClick.invoke() },
        model = imageUrl,
        contentDescription = "",
        contentScale = contentScale
    )
}


