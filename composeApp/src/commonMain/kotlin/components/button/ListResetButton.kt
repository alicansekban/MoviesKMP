package components.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.ListResetButton(
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(
            5.dp
        ),
        modifier = Modifier
            .width(48.dp)
            .height(48.dp)
            .padding(end = 8.dp, bottom = 8.dp)
            .align(Alignment.BottomEnd)
            .fillMaxSize()
            .clickable {
                // Kullanıcı düğmeye bastığında listenin en üstüne kaydırma işlemi
                onClick.invoke()
            },
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Image(
            imageVector = Icons.Default.KeyboardArrowUp,
            contentDescription = "Scroll to Top",
            modifier = Modifier
                .fillMaxSize().padding(10.dp)
        )
    }

}