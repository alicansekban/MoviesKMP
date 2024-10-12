package di

import data.remote.MoviesApiService
import data.remote.PersonApiService
import org.koin.dsl.module

val serviceModule = module {
    single<MoviesApiService> { MoviesApiService(get()) }
    single<PersonApiService> { PersonApiService(get()) }

}