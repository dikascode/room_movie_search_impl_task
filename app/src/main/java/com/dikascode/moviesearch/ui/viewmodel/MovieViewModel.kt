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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>?>()
    val movies: LiveData<List<Movie>?> get() = _movies

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun searchMovies(query: String) {
        viewModelScope.launch {
            _loading.value = true
            val result = withContext(Dispatchers.IO) {
                repository.searchMovies(query)
            }
            handleResult(result)
            _loading.value = false
        }
    }

    private fun handleResult(result: Result<List<Movie>>) {
        when (result) {
            is Result.Success -> {
                if (result.data.isEmpty()) {
                    _error.value = "No movies found for the search term."
                    _movies.value = emptyList()
                } else {
                    _movies.value = result.data
                    _error.value = null
                }
            }
            is Result.Error -> {
                _error.value = result.message
            }
        }
    }

    suspend fun getMovieDetails(id: String): Result<MovieDetailResponse> {
        _loading.postValue(true)
        val result = withContext(Dispatchers.IO) {
            try {
                repository.getMovieDetails(id)
            } catch (e: Exception) {
                Result.Error(e.message ?: "An unknown error occurred")
            }
        }
        _loading.postValue(false)
        return result
    }
}
