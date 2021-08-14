package com.maged.oldmovies.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.maged.oldmovies.Movie
import com.maged.oldmovies.source.repo.MovieRepo
import com.maged.oldmovies.ui.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * ViewModel for handling Movies Loading related ops
 */
class MoviesViewModel constructor(
    private val movieRepo: MovieRepo
) : BaseViewModel() {

    // movies LivewData
    val movies: LiveData<List<Movie>>
        get() = _movies
    private var _movies = MutableLiveData<List<Movie>>()
    //end

    fun loadMovies() {
        viewModelScope.launch {
            try {
                showLoading()

                _movies.value = movieRepo.loadMovies()

            } catch (e: Exception) {
                e.printStackTrace()
                loadingError()
            } finally {
                hideLoading()
            }
        }
    }
}