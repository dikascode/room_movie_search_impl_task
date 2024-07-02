package com.dikascode.moviesearch.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dikascode.moviesearch.data.repository.Result
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

    private val _movies = MutableLiveData<List<Movie>?>()
    val movies: MutableLiveData<List<Movie>?> get() = _movies

    private val _movieDetail = MutableLiveData<MovieDetailResponse?>()
    val movieDetail: MutableLiveData<MovieDetailResponse?> get() = _movieDetail

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: MutableLiveData<String?> get() = _error

    fun searchMovies(query: String) {
        viewModelScope.launch {
            _loading.value = true
            when (val result = repository.searchMovies(query)) {
                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        _error.value = "No movies found for the search term."
                    } else {
                        _movies.value = result.data
                        _error.value = null
                    }
                }
                is Result.Error -> {
                    _error.value = result.message
                }
            }
            _loading.value = false
        }
    }

    fun getMovieDetails(id: String) {
        viewModelScope.launch {
            _loading.value = true
            when (val result = repository.getMovieDetails(id)) {
                is Result.Success -> {
                    _movieDetail.value = result.data
                    _error.value = null
                }
                is Result.Error -> {
                    _error.value = result.message
                }
            }
            _loading.value = false
        }
    }
}
