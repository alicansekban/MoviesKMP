package components.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


@Composable
fun LoadingDialog(
    dismissOnBackPress: Boolean = false,
    dismissOnClickOutside: Boolean = false,
) {
    Dialog(
        onDismissRequest = { },
        DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        )
    ) {
        DialogContent()

    }

}

@Composable
fun DialogContent(
) {
    Card(
        modifier = Modifier,
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary),
    ) {
        CircularProgressIndicator(
            color = Color.Red,
            modifier = Modifier.padding(10.dp)
        )
    }
}
