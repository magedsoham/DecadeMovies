package com.maged.oldmovies.di

import com.maged.oldmovies.source.database.LocalDataSource
import com.maged.oldmovies.source.network.RemoteDataSource
import com.maged.oldmovies.source.repo.MovieRepo
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MyModule::class
    ]
)
/**
 * Hilt Component which exposes methods to access all sorts of objects anywhere in the app
 */
interface MyComponent {


    @Singleton
    fun getLocalDataSource(): LocalDataSource

    @Singleton
    fun getRemoteDataSource(): RemoteDataSource

    @Singleton
    fun getMovieRepo(): MovieRepo

}