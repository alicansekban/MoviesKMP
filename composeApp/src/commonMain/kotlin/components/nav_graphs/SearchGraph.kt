package components.nav_graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import utils.SearchHost
import utils.SearchRoute

fun NavGraphBuilder.searchGraph(navController: NavController) {

    navigation<SearchHost>(
        startDestination = SearchRoute,
    ) {
        composable<SearchRoute> {

        }

    }
}