package di

import domain.interactors.movie.FavoritesInteractor
import domain.interactors.movie.HomeInteractor
import domain.interactors.movie.MovieDetailInteractor
import domain.interactors.movie.MovieListInteractor
import domain.interactors.movie.SearchMovieInteractor
import domain.interactors.person.PersonInteractor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val interactorModule = module {
    singleOf(::HomeInteractor)
    singleOf(::MovieDetailInteractor)
    singleOf(::MovieListInteractor)
    singleOf(::SearchMovieInteractor)
    singleOf(::PersonInteractor)
    singleOf(::FavoritesInteractor)

}