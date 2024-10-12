package di

import data.repository.MoviesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val provideRepositoryModule = module {
    singleOf(::MoviesRepository)

}