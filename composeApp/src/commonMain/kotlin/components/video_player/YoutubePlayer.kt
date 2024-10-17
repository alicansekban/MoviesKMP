package components.video_player

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import chaintech.videoplayer.model.PlayerConfig
import chaintech.videoplayer.ui.youtube.YouTubePlayerView


@Composable
fun YoutubePlayer(
    modifier: Modifier = Modifier,
    youtubeVideoId: String,
    playerConfig: PlayerConfig = PlayerConfig()
) {

    YouTubePlayerView(
        modifier = modifier,
        videoId = youtubeVideoId,
        playerConfig = playerConfig
    )
}
