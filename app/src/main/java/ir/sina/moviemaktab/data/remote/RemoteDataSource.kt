package ir.sina.moviemaktab.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val movieApiService: MovieApiService) {
    suspend fun getAllMovies(page: Int) = movieApiService.getAllMovies(page)


}