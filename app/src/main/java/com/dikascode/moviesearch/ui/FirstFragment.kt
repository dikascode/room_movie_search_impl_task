package com.dikascode.moviesearch.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dikascode.moviesearch.R
import com.dikascode.moviesearch.databinding.FragmentFirstBinding
import com.dikascode.moviesearch.ui.adapter.MovieAdapter
import com.dikascode.moviesearch.ui.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val movieViewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieAdapter { movie ->

        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
        }

        binding.searchButton.setOnClickListener {
            val query = binding.searchEditText.text.trim().toString()
            if (query.isNotEmpty()) {
                binding.progressBar.visibility = View.VISIBLE
                movieViewModel.searchMovies(query)
            }
        }

        movieViewModel.movies.observe(viewLifecycleOwner) { movies ->
            binding.progressBar.visibility = View.GONE
            movieAdapter.setMovies(movies)
        }

        movieViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}