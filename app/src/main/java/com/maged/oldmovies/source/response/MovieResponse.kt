package com.maged.oldmovies.source.response

import com.maged.oldmovies.Movie


/**
 * Used to handle movies being parsed from JSON built-in file
 */
data class MovieResponse(val movies: List<Movie>)