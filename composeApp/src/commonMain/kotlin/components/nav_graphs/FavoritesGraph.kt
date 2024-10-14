package components.nav_graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ui.favorites.FavoriteMoviesScreen
import utils.FavoritesHost
import utils.FavoritesRoute
import utils.MovieDetailRoute

fun NavGraphBuilder.favoritesGraph(navController: NavController) {

    navigation<FavoritesHost>(
        startDestination = FavoritesRoute,
    ) {
        composable<FavoritesRoute> {
            FavoriteMoviesScreen(
                openMovieDetailScreen = { movieId ->
                    val route = MovieDetailRoute(movieId)
                    navController.navigate(route)
                }
            )
        }

    }
}