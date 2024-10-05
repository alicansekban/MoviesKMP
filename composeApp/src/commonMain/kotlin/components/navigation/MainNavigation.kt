package components.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import components.nav_graphs.favoritesGraph
import components.nav_graphs.homeGraph
import components.nav_graphs.searchGraph
import utils.HomeHost


@Composable
fun MainNavigation(
    navController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeHost,
        modifier = modifier,

        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(200, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Left
            )
        },
        exitTransition = {
            slideOutOfContainer(
                animationSpec = tween(200, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Left
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                animationSpec = tween(200, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Right
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                animationSpec = tween(200, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Right
            )
        }
    ) {
        homeGraph(navController)
        searchGraph(navController)
        favoritesGraph(navController)

    }

}