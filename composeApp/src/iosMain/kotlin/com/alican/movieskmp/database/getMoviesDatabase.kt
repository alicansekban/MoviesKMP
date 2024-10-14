package com.alican.movieskmp.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import data.local.AppDatabase
import platform.Foundation.NSHomeDirectory

fun getMoviesDatabase(): AppDatabase {
    val dbFile = NSHomeDirectory() + "/movies.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile,
        factory = { AppDatabase::class.instantiateImpl() }
    )
        .setDriver(BundledSQLiteDriver())
        .build()

}