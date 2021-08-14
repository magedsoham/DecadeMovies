package com.maged.oldmovies.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.maged.oldmovies.source.repo.MovieRepo
import com.maged.oldmovies.ui.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * ViewModel for handling Movie Details related ops
 */
class MovieDetailsViewModel constructor(
    private val movieRepo: MovieRepo
) : BaseViewModel() {

    // movies LivewData
    val picUrls: LiveData<List<String>>
        get() = _picUrls
    private var _picUrls = MutableLiveData<List<String>>()
    //end

    fun loadPics(movieName: String) {
        viewModelScope.launch {

            try {
                showLoading()

                _picUrls.value = movieRepo.loadPicUrls(movieName)

            } catch (e: Exception) {
                e.printStackTrace()
                loadingError()
            } finally {
                hideLoading()
            }
        }
    }

}