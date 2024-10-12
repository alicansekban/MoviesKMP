package di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ui.home.HomeViewModel
import ui.movie_detail.MovieDetailViewModel
import ui.movie_list.MovieListViewModel
import ui.search.SearchMovieViewModel

val provideViewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::MovieListViewModel)
    viewModelOf(::MovieDetailViewModel)
    viewModelOf(::SearchMovieViewModel)
}