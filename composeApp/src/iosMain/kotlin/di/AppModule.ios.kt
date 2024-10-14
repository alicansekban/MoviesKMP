package di

import com.alican.movieskmp.database.getMoviesDatabase
import data.local.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module {
    return module {
        single<AppDatabase> { getMoviesDatabase() }
    }
}