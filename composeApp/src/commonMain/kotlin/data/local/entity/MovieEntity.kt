package data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val movieId: Int = 0,
    val movieTitle: String = "",
    val movieOverview: String = "",
    val moviePoster: String = ""
)
