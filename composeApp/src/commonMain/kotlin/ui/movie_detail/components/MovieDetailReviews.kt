package ui.movie_detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.models.MovieReviewsUIModel

@Composable
fun MovieDetailReview(
    modifier: Modifier = Modifier,
    reviews: List<MovieReviewsUIModel>,
    onSeeAllClick: () -> Unit
) {

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Reviews",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "See All",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.clickable {
                    onSeeAllClick.invoke()
                }
            )
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                items = reviews,
                key = { it.id ?: 0 }
            ) {
                Column(modifier = Modifier.width(200.dp).height(200.dp)) {
                    it.author?.let { it1 -> Text(text = "Author: $it1",style = MaterialTheme.typography.titleMedium) }
                    it.content?.let { it1 -> Text(it1,style = MaterialTheme.typography.bodyMedium) }
                }
            }
        }
    }


}