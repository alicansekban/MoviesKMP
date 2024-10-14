package di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ui.home.HomeViewModel
import ui.movie_detail.MovieDetailViewModel
import ui.movie_list.MovieListViewModel
import ui.movie_reviews.MovieReviewsViewModel
import ui.person.PersonDetailViewModel
import ui.search.SearchMovieViewModel

val provideViewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::MovieListViewModel)
    viewModelOf(::MovieDetailViewModel)
    viewModelOf(::SearchMovieViewModel)
    viewModelOf(::MovieReviewsViewModel)
    viewModelOf(::PersonDetailViewModel)
}