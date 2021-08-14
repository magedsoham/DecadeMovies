package com.maged.oldmovies.source.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maged.oldmovies.Movie


@Entity(tableName = "Movie")
/**
 * "Movie" table in local DB
 */
data class MovieModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val title: String,
    val year: Int,
    val cast: List<String>,
    val genres: List<String>,
    val rating: Int
) {
    fun toMovie(): Movie {
        return Movie(
            title = this.title,
            year = this.year,
            cast = this.cast,
            genres = this.genres,
            rating = this.rating
        )
    }

    override fun equals(other: Any?): Boolean {
        if (other is MovieModel) {
            return title == other.title
                    && year == other.year
                    && cast == other.cast
                    && genres == other.genres
                    && rating == other.rating
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + year
        result = 31 * result + cast.hashCode()
        result = 31 * result + genres.hashCode()
        result = 31 * result + rating
        return result
    }
}