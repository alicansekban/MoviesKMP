package ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import components.bottom_bar.BottomBar
import components.navigation.MainNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import utils.FavoritesRoute
import utils.HomeRoute
import utils.SearchRoute

@Composable
@Preview
fun App() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val showBottomBar by remember {
        derivedStateOf {
            navBackStackEntry?.destination?.hasRoute<SearchRoute>() == true ||
                    navBackStackEntry?.destination?.hasRoute<FavoritesRoute>() == true ||
                    navBackStackEntry?.destination?.hasRoute<HomeRoute>() == true
        }
    }

    MaterialTheme {
        KoinContext {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    BottomBar(navController, isBottomBarVisible = showBottomBar)
                }
            ) { paddingValues ->
                MainNavigation(navController, modifier = Modifier.padding(paddingValues))

            }
        }
    }
}