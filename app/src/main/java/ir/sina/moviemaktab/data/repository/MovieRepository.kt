package ir.sina.moviemaktab.data.repository

import ir.sina.moviemaktab.data.local.db.MovieDao
import ir.sina.moviemaktab.data.remote.MovieApiService
import ir.sina.moviemaktab.model.dto.MovieDto
import ir.sina.moviemaktab.model.dto.MoviesResponseDto
import ir.sina.moviemaktab.model.ui.MovieItem
import ir.sina.moviemaktab.util.Mapper
import ir.sina.moviemaktab.util.ResponseState
import ir.sina.moviemaktab.util.asMovieEntity
import kotlinx.coroutines.delay
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApiService: MovieApiService,
    private val movieDao: MovieDao
) {
    suspend fun getMovieList(page: Int): ResponseState<List<MovieItem>> {
        return try {
            delay(2000)
            val movieListResponse = movieApiService.getAllMovies(page)
            val movieEntityList =
                movieListResponse.result.map { movieDto -> movieDto.asMovieEntity() }

            movieDao.insertAll(movieEntityList)

            ResponseState.Success(Mapper.movieEntityToItem(movieEntityList))
        } catch (e: Exception) {
            return ResponseState.Error(e.message.toString())
        }
    }
}