package di

import org.koin.dsl.module
import ui.home.HomeViewModel
import ui.movie_detail.MovieDetailViewModel
import ui.movie_list.MovieListViewModel

val provideViewModelModule = module {
    single {
        HomeViewModel(get())
    }
    single {
        MovieListViewModel(get())
    }
    single {
        MovieDetailViewModel(get())
    }

}