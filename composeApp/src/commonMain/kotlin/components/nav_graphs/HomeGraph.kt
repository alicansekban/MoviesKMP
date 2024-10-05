package components.nav_graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ui.home.HomeScreen
import ui.movie_list.MovieListScreen
import utils.HomeHost
import utils.HomeRoute
import utils.MovieListRoute

fun NavGraphBuilder.homeGraph(navController: NavController) {

    navigation<HomeHost>(
        startDestination = HomeRoute,
    ) {
        composable<HomeRoute> {
            HomeScreen(
            )
        }

        composable<MovieListRoute> {
            MovieListScreen()
        }
    }
}