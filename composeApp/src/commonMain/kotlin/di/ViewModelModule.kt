package di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ui.favorites.FavoriteMoviesViewModel
import ui.home.HomeViewModel
import ui.movie_detail.MovieDetailViewModel
import ui.movie_list.MovieListViewModel
import ui.movie_reviews.MovieReviewsViewModel
import ui.person.PersonDetailViewModel
import ui.search.SearchMovieViewModel
import ui.video.VideoScreenViewModel

val provideViewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { MovieListViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
    viewModel { SearchMovieViewModel(get()) }
    viewModel { MovieReviewsViewModel(get()) }
    viewModel { PersonDetailViewModel(get()) }
    viewModel { FavoriteMoviesViewModel(get()) }
    viewModel { VideoScreenViewModel(get()) }
}