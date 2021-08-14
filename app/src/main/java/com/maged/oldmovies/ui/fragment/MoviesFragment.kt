package com.maged.oldmovies.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.maged.oldmovies.AppClass
import com.maged.oldmovies.Movie
import com.maged.oldmovies.databinding.FragmentMoviesBinding
import com.maged.oldmovies.enums.FragType
import com.maged.oldmovies.ui.adapter.MoviesRecyclerViewAdapter
import com.maged.oldmovies.ui.base.BaseFragment
import com.maged.oldmovies.ui.viewModel.MoviesViewModel
import com.maged.oldmovies.ui.viewModel.factory.MoviesViewModelFactory
import com.maged.oldmovies.utils.Utils.Companion.runWithCaution
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class MoviesFragment : BaseFragment() {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!


    private lateinit var viewModel: MoviesViewModel

    private lateinit var moviesAdapter: MoviesRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val view = binding.root
        findRecyclerView(view)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        runWithCaution({
            currentFrag(FragType.Movies)
            setupRecyclerView(1)
            setupViewModel()
            setupFilterMoviesCallback {
                moviesAdapter.filter.filter(it)
            }
        })

    }

    private fun setupViewModel() {
        // initialize
        val movieRepo = AppClass.app.getMyComponent().getMovieRepo()
        viewModel = ViewModelProvider(
            this,
            MoviesViewModelFactory(
                movieRepo
            )
        ).get(MoviesViewModel::class.java)
        //end

        super.setupViewModel(viewModel, binding.pbMain)

        // movies related

        //display movies whenever they are updated
        viewModel.movies.observe(viewLifecycleOwner, {
            //setup adapter
            moviesAdapter = MoviesRecyclerViewAdapter(this::movieOnClick, it)
            setupAdapter(moviesAdapter)
        })

        if (viewModel.movies.value.isNullOrEmpty()) //if viewModel is loading for the first time.
            viewModel.loadMovies()
        //end
    }

    /**
     * called whenever a Movie item is clicked in the adapter, navigates to MovieDetails Fragment
     */
    private fun movieOnClick(movie: Movie) {
        val navDirections =
            MoviesFragmentDirections.actionToMovieDetailsFragment(movie)
        view?.run {
            Navigation.findNavController(this).navigate(navDirections)
        }
    }

}