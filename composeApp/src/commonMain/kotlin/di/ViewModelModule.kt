package di

import org.koin.dsl.module
import ui.home.HomeViewModel

val provideViewModelModule = module {
    single {
        HomeViewModel(get())
    }

}