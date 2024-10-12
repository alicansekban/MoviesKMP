package di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import domain.interactors.HomeInteractor
import domain.interactors.MovieDetailInteractor
import domain.interactors.MovieListInteractor
import domain.interactors.SearchMovieInteractor

val interactorModule = module {
    singleOf(::HomeInteractor)
    singleOf(::MovieDetailInteractor)
    singleOf(::MovieListInteractor)
    singleOf(::SearchMovieInteractor)

}