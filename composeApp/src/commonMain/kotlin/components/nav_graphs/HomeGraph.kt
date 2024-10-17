package components.nav_graphs

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import domain.models.movie.MovieListUIModel
import org.koin.compose.viewmodel.koinViewModel
import ui.home.HomeScreen
import ui.movie_detail.MovieDetailScreen
import ui.movie_detail.MovieDetailViewModel
import ui.movie_list.MovieListScreen
import ui.movie_list.MovieListViewModel
import ui.movie_reviews.MovieReviewsScreen
import ui.movie_reviews.MovieReviewsViewModel
import ui.person.PersonDetailScreen
import ui.person.PersonDetailViewModel
import ui.video.VideoScreen
import ui.video.VideoScreenViewModel
import utils.*

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
            val isLoaded = rememberSaveable { mutableStateOf(false) }

            if (!isLoaded.value) {
                viewModel.getMoviesByType(args.type, page = 1, MovieListUIModel())
                isLoaded.value = true
            }
            MovieListScreen(
                viewModel = viewModel,
                openMovieDetailScreen = { movieId ->
                    val route = MovieDetailRoute(movieId)
                    navController.navigate(route)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<MovieDetailRoute> {
            val args = it.toRoute<MovieDetailRoute>()
            val viewModel = koinViewModel<MovieDetailViewModel>(key = "${args.movieId}")
            val isLoaded = rememberSaveable { mutableStateOf(false) }

            if (!isLoaded.value) {
                viewModel.callApiCalls(args.movieId)
                isLoaded.value = true
            }

            MovieDetailScreen(
                viewModel = viewModel, onBackClick = {
                    navController.popBackStack()
                },
                openMovieDetailScreen = { id ->
                    val route = MovieDetailRoute(id)
                    navController.navigate(route)
                },
                movieId = args.movieId,
                openReviewScreen = {movieId ->
                    val route = MovieReviewsRoute(movieId)
                    navController.navigate(route)
                },
                openPersonDetailScreen = {personId ->
                    val route = PersonDetailRoute(personId)
                    navController.navigate(route)
                },
                openVideosScreen = {
                    val route = VideoScreenRoute(movieId = args.movieId)
                    navController.navigate(route)
                }
            )

        }

        composable<MovieReviewsRoute> {
            val args = it.toRoute<MovieReviewsRoute>()
            val viewModel = koinViewModel<MovieReviewsViewModel>()
            viewModel.getReviews(args.movieId, 1)
            MovieReviewsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                viewModel = viewModel,
                movieID = args.movieId
            )
        }

        composable<PersonDetailRoute> {
            val args = it.toRoute<PersonDetailRoute>()
            val viewModel = koinViewModel<PersonDetailViewModel>()
            viewModel.callApiCalls(args.personId)
            PersonDetailScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                viewModel = viewModel,
            )
        }

        composable<VideoScreenRoute> {
            val args = it.toRoute<VideoScreenRoute>()
            val viewModel = koinViewModel<VideoScreenViewModel>()
            viewModel.getVideos(args.movieId)
            VideoScreen(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }



    }
}