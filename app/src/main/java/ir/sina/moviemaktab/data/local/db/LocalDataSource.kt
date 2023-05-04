package ir.sina.moviemaktab.data.local.db

import ir.sina.moviemaktab.model.dto.MoviesResponseDto
import ir.sina.moviemaktab.model.entity.MovieEntity
import ir.sina.moviemaktab.model.ui.MovieItem
import ir.sina.moviemaktab.util.Mapper
import ir.sina.moviemaktab.util.ResponseState
import ir.sina.moviemaktab.util.asMovieEntity
import kotlinx.coroutines.delay
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {
    suspend fun insertAll(movieListResponse: MoviesResponseDto): ResponseState<List<MovieItem>> {
        return try {
            delay(2000)

            val movieEntityList =
                movieListResponse.result.map { movieDto -> movieDto.asMovieEntity() }

            movieDao.insertAll(movieEntityList)

            ResponseState.Success(Mapper.movieEntityToItem(movieEntityList))
        } catch (e: Exception) {
            return ResponseState.Error(e.message.toString())
        }
    }

    fun getAllMovie() = movieDao.getAllMovie()
}