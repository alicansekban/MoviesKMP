package com.alican.movieskmp.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import data.local.AppDatabase


fun getMoviesDatabase(context: Context): AppDatabase {
    val dbFile = context.getDatabasePath("movies.db")
    return Room.databaseBuilder<AppDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    ).setDriver(BundledSQLiteDriver())
        .build()
}