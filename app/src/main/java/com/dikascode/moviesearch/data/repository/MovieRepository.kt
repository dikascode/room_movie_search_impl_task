package com.dikascode.moviesearch.data.repository

import com.dikascode.moviesearch.data.model.Movie
import com.dikascode.moviesearch.data.model.MovieDetailResponse
import com.dikascode.moviesearch.data.network.OMDbApiService
import com.dikascode.moviesearch.util.toEntity
import com.dikascode.moviesearch.util.toMovieDetailResponse
import javax.inject.Inject
import javax.inject.Named

class MovieRepository @Inject constructor(
    private val apiService: OMDbApiService,
    private val movieDao: MovieDao,
    @Named("API_KEY") private val apiKey: String
) {
    suspend fun searchMovies(query: String): List<Movie> {
        val response = apiService.searchMovies(query, apiKey)
        return response.search
    }

    suspend fun getMovieDetails(id: String): MovieDetailResponse {
        val cachedMovie = movieDao.getMovieById(id)
        return if (cachedMovie != null) {
            cachedMovie.toMovieDetailResponse()
        } else {
            val response = apiService.getMovieDetails(id, apiKey)
            movieDao.insertMovie(response.toEntity())
            response
        }
    }
}

