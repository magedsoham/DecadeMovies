package com.maged.oldmovies.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.maged.oldmovies.AppClass
import com.maged.oldmovies.Movie
import com.maged.oldmovies.databinding.FragmentMovieDetailsBinding
import com.maged.oldmovies.enums.FragType
import com.maged.oldmovies.ui.adapter.MoviesPicsRecyclerViewAdapter
import com.maged.oldmovies.ui.base.BaseFragment
import com.maged.oldmovies.ui.viewModel.MovieDetailsViewModel
import com.maged.oldmovies.ui.viewModel.factory.MovieDetailsViewModelFactory
import com.maged.oldmovies.utils.Utils.Companion.runWithCaution
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment used to display a single Movie details
 */
@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var movie: Movie //passed on as argument

    private lateinit var viewModel: MovieDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
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
            getArgs()
            currentFrag(FragType.MovieDetails, movie.title)
            setupRecyclerView(2)
            setupViewModel()
            displayMovieData()
        })

    }

    private fun getArgs() {
        movie = MovieDetailsFragmentArgs.fromBundle(requireArguments()).movie
    }

    private fun setupViewModel() {
        // initialize
        val movieRepo = AppClass.app.getMyComponent().getMovieRepo()

        viewModel = ViewModelProvider(
            this,
            MovieDetailsViewModelFactory(
                movieRepo
            )
        ).get(MovieDetailsViewModel::class.java)
        //end

        super.setupViewModel(viewModel, binding.pbPic)

        //display pics whenever they are updated
        viewModel.picUrls.observe(viewLifecycleOwner, {
            runWithCaution({
                setupAdapter(
                    MoviesPicsRecyclerViewAdapter(it)
                )
            })
        })
    }

    private fun displayMovieData() {
        binding.tvRatingValue.text = "${movie.rating.toFloat()}"
        binding.rbRating.rating = movie.rating.toFloat()
        binding.tvYearValue.text = "${movie.year}"

        binding.cvGenres.visibility =
            if (movie.genres.isEmpty())
                View.GONE
            else
                View.VISIBLE
        binding.tvGenresValue.text = movie.genres.joinToString(separator = " - ")

        binding.cvCast.visibility =
            if (movie.cast.isEmpty())
                View.GONE
            else
                View.VISIBLE

        binding.tvCastValue.text = movie.cast.joinToString(separator = "\n")

        viewModel.loadPics(movie.title)
    }

}