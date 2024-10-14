package data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import data.local.dao.MovieDao
import data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): MovieDao
}