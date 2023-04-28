package ir.sina.moviemaktab.data.repository

import ir.sina.moviemaktab.data.local.db.MovieDao
import ir.sina.moviemaktab.data.remote.MovieApiService
import ir.sina.moviemaktab.model.ui.MovieItem
import ir.sina.moviemaktab.util.Mapper
import ir.sina.moviemaktab.util.asMovieEntity
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApiService: MovieApiService,
    private val movieDao: MovieDao
) {
    suspend fun getMovieList(page: Int): List<MovieItem> {
        val movieListResponse = movieApiService.getAllMovies(page)
        val movieEntityList = movieListResponse.map { movieDto -> movieDto.asMovieEntity() }
        movieDao.insertAll(movieEntityList)
        return Mapper.movieEntityToItem(movieEntityList)
    }
}