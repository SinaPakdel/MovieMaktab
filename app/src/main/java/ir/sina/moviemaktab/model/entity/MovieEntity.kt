package ir.sina.moviemaktab.model.entity

import androidx.room.Entity

@Entity(tableName = "movie_table")
data class MovieEntity(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val posterPath: String,
)
