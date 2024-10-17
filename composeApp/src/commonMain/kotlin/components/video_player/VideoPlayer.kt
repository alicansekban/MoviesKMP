package components.video_player

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import chaintech.videoplayer.model.PlayerConfig
import chaintech.videoplayer.ui.video.VideoPlayerView
import chaintech.videoplayer.ui.youtube.YouTubePlayerView

@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    videoUrl: String,
    playerConfig: PlayerConfig = PlayerConfig()

) {
    VideoPlayerView(
        modifier = modifier,
        url = videoUrl,
        playerConfig = playerConfig
    )

}