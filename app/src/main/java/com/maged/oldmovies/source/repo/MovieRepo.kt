package com.maged.oldmovies.source.repo

import com.maged.oldmovies.AppClass
import com.maged.oldmovies.Movie
import com.maged.oldmovies.source.database.LocalDataSource
import com.maged.oldmovies.source.network.RemoteDataSource
import com.maged.oldmovies.source.response.MovieResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Movie Repo acting as an intermediate layer between viewModels & Data sources, knowing how to communicate with each of them with its built-in logic.
 */
class MovieRepo(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun loadMovies(): List<Movie> {
        val result: MutableList<Movie> = arrayListOf()

        withContext(Dispatchers.IO) {
            val database = localDataSource.database
            val movieDAO = database.getMovieDAO()
            var dbMovies = movieDAO.getAllMovies()

            if (dbMovies.isNullOrEmpty()) {

                // app is running for first time
                //load from Json file
                val moviesFromJson = getMoviesFromJson()

                //map to DB Model
                val movieModels = moviesFromJson.map { it.toMovieModel() }

                //insert into DB
                movieDAO.insert(movieModels)

                dbMovies = movieModels
                //end
            }

            result.addAll(dbMovies.map { it.toMovie() })
        }

        return result
    }

    private fun getMoviesFromJson(): List<Movie> {
        val movies: MutableList<Movie> = arrayListOf()
        try {
            val inputStream = AppClass.app.applicationContext.assets.open("movies.json")
            val buffer = ByteArray(size = inputStream.available())
            inputStream.read(buffer)
            inputStream.close()

            val jsonRaw = String(buffer)

            val moshi = Moshi.Builder()
                .build()

            val adapter: JsonAdapter<MovieResponse> =
                moshi.adapter(
                    MovieResponse::class.java
                )

            val response = adapter.fromJson(jsonRaw)
            response?.run {//safety, should always be true
                movies.addAll(this.movies)
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

        return movies
    }


    suspend fun loadPicUrls(movieName: String): MutableList<String> {

        val result: MutableList<String> = arrayListOf()

        val movieService = remoteDataSource.movieService
        val search = movieService.search(text = movieName)

        search.photosRoot?.photos?.forEach {

            val url = it.id?.run {
                //catching in order to skip whoever fails and proceed to the next photo
                try {
                    val sizes = movieService.getSizes(photoId = this)
                    sizes.photoSizes?.getUrl()
                } catch (e: Exception) {
                    e.printStackTrace()
                    ""
                }
            }
            url?.run {
                result.add(url)
            }
        }

        return result
    }
}