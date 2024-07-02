package com.dikascode.moviesearch.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dikascode.moviesearch.data.model.Movie
import com.dikascode.moviesearch.data.model.MovieDetailResponse
import com.dikascode.moviesearch.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private val _movieDetail = MutableLiveData<MovieDetailResponse>()
    val movieDetail: LiveData<MovieDetailResponse> get() = _movieDetail

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun searchMovies(query: String) {
        viewModelScope.launch {
            _loading.value = true
            val results = repository.searchMovies(query)
            _movies.value = results
            _loading.value = false
        }
    }

    fun getMovieDetails(id: String) {
        viewModelScope.launch {
            _loading.value = true
            val detail = repository.getMovieDetails(id)
            _movieDetail.value = detail
            _loading.value = false
        }
    }
}
