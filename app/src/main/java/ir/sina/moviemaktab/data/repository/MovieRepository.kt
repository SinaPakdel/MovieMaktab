package ir.sina.moviemaktab.data.repository

import ir.sina.moviemaktab.data.local.db.LocalDataSource
import ir.sina.moviemaktab.data.local.db.MovieDao
import ir.sina.moviemaktab.data.remote.MovieApiService
import ir.sina.moviemaktab.data.remote.RemoteDataSource
import ir.sina.moviemaktab.model.dto.MovieDto
import ir.sina.moviemaktab.model.dto.MoviesResponseDto
import ir.sina.moviemaktab.model.ui.MovieItem
import ir.sina.moviemaktab.util.Mapper
import ir.sina.moviemaktab.util.ResponseState
import ir.sina.moviemaktab.util.asMovieEntity
import ir.sina.moviemaktab.util.safeApiCall
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    suspend fun getMovieList(page: Int): ResponseState<List<MovieItem>> =
        localDataSource.insertAll(remoteDataSource.getAllMovies(page).body()!!)

    suspend fun getMovieListRemote(page: Int): Flow<ResponseState<MoviesResponseDto>> = safeApiCall {
        remoteDataSource.getAllMovies(page)
    }
}