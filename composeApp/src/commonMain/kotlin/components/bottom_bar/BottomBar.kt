package components.bottom_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import movieskmp.composeapp.generated.resources.Res
import movieskmp.composeapp.generated.resources.favorites
import movieskmp.composeapp.generated.resources.home
import movieskmp.composeapp.generated.resources.search
import org.jetbrains.compose.resources.stringResource
import utils.FavoritesHost
import utils.HomeHost
import utils.SearchHost

@Composable
fun BottomBar(
    navController: NavController,
    isBottomBarVisible: Boolean
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val items = listOf(
        BottomBarRoute(
            name = stringResource(Res.string.home),
            route = HomeHost,
            unSelectedIcon = Icons.Outlined.Home,
            selectedIcon = Icons.Filled.Home
        ),
        BottomBarRoute(
            name = stringResource(Res.string.search),
            route = SearchHost,
            unSelectedIcon = Icons.Outlined.Search,
            selectedIcon = Icons.Filled.Search
        ),
        BottomBarRoute(
            name = stringResource(Res.string.favorites),
            route = FavoritesHost,
            unSelectedIcon = Icons.Filled.FavoriteBorder,
            selectedIcon = Icons.Filled.Favorite

        )
    )
    var selectedIndex by rememberSaveable {
        mutableStateOf(0)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        AnimatedVisibility(visible = isBottomBarVisible) {
            NavigationBar(
                containerColor = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                items.forEachIndexed { index,item ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true,
                        onClick = {
                            selectedIndex = index
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = {
                            Text(
                                text = item.name,
                                fontSize = 10.sp,
                            )
                        },
                        icon = {
                            val icon = if (index == selectedIndex) item.selectedIcon else item.unSelectedIcon
                            Icon(icon, contentDescription = item.name)
                        },
                        interactionSource = NoRippleInteractionSource,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Red,
                            selectedTextColor = Color.Red,
                            unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                            unselectedTextColor = MaterialTheme.colorScheme.onBackground,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    }
}

private object NoRippleInteractionSource : MutableInteractionSource {

    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction) = true
}