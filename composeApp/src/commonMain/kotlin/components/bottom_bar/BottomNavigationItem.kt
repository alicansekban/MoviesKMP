package components.bottom_bar

import androidx.compose.ui.graphics.vector.ImageVector


data class BottomBarRoute<T : Any>(val name: String, val route: T, val unSelectedIcon: ImageVector,val selectedIcon: ImageVector)

