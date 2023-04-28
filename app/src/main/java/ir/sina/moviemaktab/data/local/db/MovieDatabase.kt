package ir.sina.moviemaktab.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.sina.moviemaktab.model.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}