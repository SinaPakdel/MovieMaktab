package ir.sina.moviemaktab.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("movie_id")
    val id: Int,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("poster_path")
    val posterPath: String
)
