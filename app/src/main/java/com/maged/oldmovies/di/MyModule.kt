package com.maged.oldmovies.di

import android.content.Context
import androidx.room.Room
import com.maged.oldmovies.AppClass
import com.maged.oldmovies.source.database.AppDatabase
import com.maged.oldmovies.source.database.LocalDataSource
import com.maged.oldmovies.source.network.MovieService
import com.maged.oldmovies.source.network.RemoteDataSource
import com.maged.oldmovies.source.repo.MovieRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
/**
 * Hilt Module which specifies how to get each & every required dependency
 */
class MyModule {

    @Provides
    @Singleton
    fun getApplicationContext(): Context = AppClass.app

    @Provides
    @Singleton
    fun provideMyDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "MoviesDB")
            .build()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(appDatabase: AppDatabase): LocalDataSource {
        return LocalDataSource(appDatabase)
    }

    @Provides
    @Singleton
    fun provideMovieService(): MovieService {
        return MovieService.create()
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(movieService: MovieService): RemoteDataSource {
        return RemoteDataSource(movieService)
    }

    @Provides
    @Singleton
    fun provideMovieRepo(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): MovieRepo {
        return MovieRepo(localDataSource, remoteDataSource)
    }
}