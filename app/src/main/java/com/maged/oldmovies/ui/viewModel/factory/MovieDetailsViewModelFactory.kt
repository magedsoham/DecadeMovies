package com.maged.oldmovies.ui.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maged.oldmovies.source.repo.MovieRepo
import com.maged.oldmovies.ui.viewModel.MovieDetailsViewModel
import javax.inject.Inject

/**
 * Factory class used to pass on MovieRepo as argument
 */
class MovieDetailsViewModelFactory @Inject
constructor(
    private val movieRepo: MovieRepo
) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(movieRepo) as T
    }

}