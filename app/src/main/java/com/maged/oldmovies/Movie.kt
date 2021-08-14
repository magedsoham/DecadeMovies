package com.maged.oldmovies

import com.maged.oldmovies.source.database.model.MovieModel
import java.io.Serializable

/**
 * Used for communication between ViewModels & Repo & Activity & Adapter
 */
data class Movie(
    val title: String,
    val year: Int,
    val cast: List<String>,
    val genres: List<String>,
    val rating: Int
) : Serializable {
    fun toMovieModel(): MovieModel {
        return MovieModel(
            title = this.title,
            year = this.year,
            cast = this.cast,
            genres = this.genres,
            rating = this.rating
        )
    }
}