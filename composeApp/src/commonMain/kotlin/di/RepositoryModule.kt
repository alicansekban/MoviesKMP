package di

import data.remote.ApiService
import data.repository.MoviesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val provideRepositoryModule = module {
    single<ApiService> { ApiService(get()) }
    singleOf(::MoviesRepository)

}