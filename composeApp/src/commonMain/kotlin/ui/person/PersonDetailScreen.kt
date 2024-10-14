package ui.person

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import components.imageView.CustomImageView
import components.top_bar.CustomTopBar
import domain.models.BaseUIModel

@Composable
fun PersonDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: PersonDetailViewModel,
    onBackClick: () -> Unit,
) {
    
    val personState by viewModel.personDetail.collectAsStateWithLifecycle()
    
    Column(
        modifier = modifier.fillMaxSize().background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {  
        CustomTopBar(
            title = "Person Detail"
        ) { 
            onBackClick.invoke()
        }
        
        Column(modifier = Modifier.fillMaxSize()) {
            when (personState) {
                BaseUIModel.Empty -> {}
                is BaseUIModel.Error -> {}
                BaseUIModel.Loading -> {}
                is BaseUIModel.Success -> {
                    val data = (personState as BaseUIModel.Success).data
                    CustomImageView(
                        imageUrl = data.profilePath ?: "",
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.50f)
                    )
                }
            }
        }
    }

}