package ui.person

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import components.dialog.LoadingDialog
import components.imageView.CustomImageView
import components.top_bar.CustomTopBar
import domain.models.BaseUIModel
import movieskmp.composeapp.generated.resources.Res
import movieskmp.composeapp.generated.resources.person_detail
import org.jetbrains.compose.resources.stringResource

@Composable
fun PersonDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: PersonDetailViewModel,
    onBackClick: () -> Unit,
) {
    
    val personState by viewModel.personDetail.collectAsStateWithLifecycle()
    val personImages by viewModel.personImages.collectAsStateWithLifecycle()
    
    Column(
        modifier = modifier.fillMaxSize().background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {  
        CustomTopBar(
            title = stringResource(Res.string.person_detail),
        ) { 
            onBackClick.invoke()
        }
        
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {

            when (personImages) {
                BaseUIModel.Empty -> {}
                is BaseUIModel.Error<*> ->{}
                BaseUIModel.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.Red
                        )
                    }
                }
                is BaseUIModel.Success<*> -> {
                    val data = (personImages as BaseUIModel.Success<List<String>>).data
                    PersonImagesPager(
                        images = data,
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.50f)
                    )
                }
            }

            when (personState) {
                BaseUIModel.Empty -> {}
                is BaseUIModel.Error -> {}
                BaseUIModel.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.Red
                        )
                    }
                }
                is BaseUIModel.Success -> {
                    val data = (personState as BaseUIModel.Success).data
                    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically){

                            data.name?.let { Text(it) }
                            data.birthday?.let { Text(it) }
                        }
                        data.biography?.let { Text(it) }
                    }
                }
            }
        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PersonImagesPager(
    modifier: Modifier = Modifier,
    images: List<String>,
) {

    val pagerState = rememberPagerState(pageCount = { images.size })

        HorizontalPager(pagerState) { index ->
            val currentMovie = images[index]
            CustomImageView(
                imageUrl = currentMovie,
                modifier = modifier,
                onClick = {}
            )

        }
}