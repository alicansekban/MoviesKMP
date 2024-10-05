package di

import data.remote.ApiService
import data.repository.MoviesRepository
import domain.interactors.HomeInteractor
import domain.interactors.MovieDetailInteractor
import domain.interactors.MovieListInteractor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val provideRepositoryModule = module {
    single<ApiService> { ApiService(get()) }
    singleOf(::MoviesRepository)
    singleOf(::HomeInteractor)
    singleOf(::MovieDetailInteractor)
    singleOf(::MovieListInteractor)

}