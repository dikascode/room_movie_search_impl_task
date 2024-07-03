package com.dikascode.moviesearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dikascode.moviesearch.data.model.MovieDetailResponse
import com.dikascode.moviesearch.data.repository.Result
import com.dikascode.moviesearch.databinding.FragmentMovieListBinding
import com.dikascode.moviesearch.ui.adapter.MovieAdapter
import com.dikascode.moviesearch.ui.adapter.MovieDetailCallback
import com.dikascode.moviesearch.ui.viewmodel.MovieViewModel
import com.dikascode.moviesearch.util.KeyboardUtils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment(), MovieDetailCallback {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private val movieViewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchButton()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter { movie ->
            retrieveMovieDetails(movie.imdbID)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
        }
    }

    private fun setupSearchButton() {
        binding.searchButton.setOnClickListener {
            val query = binding.searchEditText.text.trim().toString()
            if (query.isNotEmpty()) {
                hideKeyboard(requireContext(), binding.searchEditText)
                movieViewModel.searchMovies(query)
            } else {
                Toast.makeText(requireContext(), "Please enter a query to search", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        movieViewModel.movies.observe(viewLifecycleOwner) { movies ->
            if (movies.isNullOrEmpty()) {
                binding.noResultsTextView.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.noResultsTextView.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                movieAdapter.submitList(movies)
            }
        }

        movieViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }


        movieViewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                showSnackar(it)
            }
        }
    }

    private fun showSnackar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }

    private fun retrieveMovieDetails(imdbID: String) {
        lifecycleScope.launch {
            when (val result = movieViewModel.getMovieDetails(imdbID)) {
                is Result.Success -> onMovieDetailRetrieved(result.data)
                is Result.Error -> showSnackar(result.message)
            }
        }
    }

    override fun onMovieDetailRetrieved(movieDetail: MovieDetailResponse) {
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(movieDetail)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
