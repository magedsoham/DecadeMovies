package com.maged.oldmovies.source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.maged.oldmovies.source.database.dao.MovieDAO
import com.maged.oldmovies.source.database.model.MovieModel

@Database(
    entities = [
        MovieModel::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getMovieDAO(): MovieDAO
}