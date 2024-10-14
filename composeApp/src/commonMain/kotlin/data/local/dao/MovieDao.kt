package data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Query("DELETE FROM movies WHERE movieId = :movieId")
    suspend fun removeMovie(movieId: Int)

    @Query("SELECT * FROM movies WHERE movieId = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity?

}