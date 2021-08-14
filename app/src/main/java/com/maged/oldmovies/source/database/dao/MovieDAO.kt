package com.maged.oldmovies.source.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maged.oldmovies.source.database.model.MovieModel

@Dao
/**
 * DAO interface for Movie DB Ops
 */
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieModels: List<MovieModel>)

    @Query("SELECT * FROM movie")
    suspend fun getAllMovies(): List<MovieModel>

    @Query("DELETE FROM movie")
    suspend fun deleteAllMovies()
}