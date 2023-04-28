package ir.sina.moviemaktab.util

import ir.sina.moviemaktab.model.dto.MovieDto
import ir.sina.moviemaktab.model.entity.MovieEntity
import ir.sina.moviemaktab.model.ui.MovieItem

object Mapper {
    fun movieEntityToItem(movieListResponse: List<MovieEntity>): List<MovieItem> {
        val movieListItem = movieListResponse.map { movieItem ->
            movieItem.run {
                MovieItem(id, title, posterPath)
            }
        }
        return movieListItem
    }
}

fun MovieDto.asMovieEntity(): MovieEntity {
    return MovieEntity(id, title, posterPath)
}