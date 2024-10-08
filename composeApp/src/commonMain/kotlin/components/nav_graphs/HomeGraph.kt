package components.nav_graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import domain.models.MovieListUIModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import ui.home.HomeScreen
import ui.movie_detail.MovieDetailScreen
import ui.movie_detail.MovieDetailViewModel
import ui.movie_list.MovieListScreen
import ui.movie_list.MovieListViewModel
import utils.HomeHost
import utils.HomeRoute
import utils.MovieDetailRoute
import utils.MovieListRoute

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.homeGraph(navController: NavController) {

    navigation<HomeHost>(
        startDestination = HomeRoute,
    ) {
        composable<HomeRoute> {
            HomeScreen(
                openListScreen = {
                    val route = MovieListRoute(it)
                    navController.navigate(route)
                },
                openMovieDetailScreen = {
                    val route = MovieDetailRoute(it)
                    navController.navigate(route)
                }
            )
        }

        composable<MovieListRoute> {
            val args = it.toRoute<MovieListRoute>()
            val viewModel = koinViewModel<MovieListViewModel>()
            viewModel.getMoviesByType(args.type, page = 1, MovieListUIModel())
            MovieListScreen(
                viewModel = viewModel,
                openMovieDetailScreen = {
                    val route = MovieDetailRoute(it)
                    navController.navigate(route)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<MovieDetailRoute> {
            val args = it.toRoute<MovieDetailRoute>()
            val viewModel = koinViewModel<MovieDetailViewModel>()
            viewModel.callApiCalls(args.movieId)
            MovieDetailScreen(
                viewModel = viewModel, onBackClick = {
                    navController.popBackStack()
                },
                openMovieDetailScreen = { id ->
                    val route = MovieDetailRoute(id)
                },
                movieId = args.movieId
            )

        }
    }
}