package ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import components.bottom_bar.BottomBar
import components.navigation.MainNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {

    val navController = rememberNavController()
    MaterialTheme {
        KoinContext {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    BottomBar(navController, isBottomBarVisible = true)
                }
            ) { paddingValues ->
                MainNavigation(navController, modifier = Modifier.padding(paddingValues))

            }
        }
    }
}