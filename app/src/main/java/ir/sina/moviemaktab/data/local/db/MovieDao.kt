package ir.sina.moviemaktab.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.sina.moviemaktab.model.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movieEntity:List< MovieEntity>)

    @Query("select * from movie_table")
    fun getAllMovie(): Flow<List<MovieEntity>>

}