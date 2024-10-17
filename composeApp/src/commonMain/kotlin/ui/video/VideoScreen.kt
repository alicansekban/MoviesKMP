package ui.video

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chaintech.videoplayer.model.PlayerConfig
import components.dialog.LoadingDialog
import components.top_bar.CustomTopBar
import components.video_player.VideoPlayer
import components.video_player.YoutubePlayer
import domain.models.BaseUIModel
import domain.models.movie.MovieVideoType
import domain.models.movie.MovieVideoUIModel
import org.koin.compose.viewmodel.koinViewModel
import utils.VideoScreenRoute

@Composable
fun VideoScreen(
    modifier: Modifier = Modifier,
    viewModel: VideoScreenViewModel,
    onBackClick: () -> Unit
) {

    val state by viewModel.video.collectAsStateWithLifecycle()

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        CustomTopBar(
            title = "Trailer"
        ) {
            onBackClick.invoke()
        }

        when (state) {
            BaseUIModel.Empty -> {}
            is BaseUIModel.Error<*> -> {}
            BaseUIModel.Loading -> {
                LoadingDialog()
            }

            is BaseUIModel.Success -> {
                val video = (state as BaseUIModel.Success<MovieVideoUIModel?>).data
                video?.let {v ->
                    if (v.videoType == MovieVideoType.YOUTUBE) {
                        YoutubePlayer(
                            modifier = Modifier.fillMaxWidth().height(500.dp),
                            youtubeVideoId = v.videoUrl ?: "",
                            playerConfig = PlayerConfig(
                                isSpeedControlEnabled = false,
                            )

                        )
                    } else {
                        VideoPlayer(
                            modifier = Modifier.fillMaxWidth().height(500.dp),
                            videoUrl = v.videoUrl ?: ""
                        )
                    }
                }
            }
        }
    }

}