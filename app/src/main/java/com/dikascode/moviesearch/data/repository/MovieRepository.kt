package com.dikascode.moviesearch.data.repository

import com.dikascode.moviesearch.data.model.Movie
import com.dikascode.moviesearch.data.model.MovieDetailResponse
import com.dikascode.moviesearch.data.model.MovieResponse
import com.dikascode.moviesearch.data.network.OMDbApiService
import com.dikascode.moviesearch.util.HttpException
import com.dikascode.moviesearch.util.NetworkException
import com.dikascode.moviesearch.util.UnknownException
import com.dikascode.moviesearch.util.toEntity
import com.dikascode.moviesearch.util.toMovieDetailResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}

class MovieRepository @Inject constructor(
    private val apiService: OMDbApiService,
    private val movieDao: MovieDao,
    @Named("API_KEY") private val apiKey: String
) {
    suspend fun searchMovies(query: String): Result<List<Movie>> {
        return try {
            val response: Response<MovieResponse> = apiService.searchMovies(query, apiKey)
            if (response.isSuccessful) {
                Result.Success(response.body()?.search ?: emptyList())
            } else {
                Result.Error("Error occurred: ${response.message()}")
            }
        } catch (e: HttpException) {
            Result.Error(e.message ?: "HTTP error")
        } catch (e: NetworkException) {
            Result.Error("Network error: ${e.message}")
        } catch (e: UnknownException) {
            Result.Error("Unknown error: ${e.message}")
        } catch (e: Exception) {
            Result.Error("An unexpected error occurred: ${e.message}")
        }
    }

    suspend fun getMovieDetails(id: String): Result<MovieDetailResponse> {
        return try {
            val cachedMovie = movieDao.getMovieById(id)
            if (cachedMovie != null) {
                Result.Success(cachedMovie.toMovieDetailResponse())
            } else {
                val response: Response<MovieDetailResponse> = apiService.getMovieDetails(id, apiKey)
                if (response.isSuccessful) {
                    response.body()?.let { movieDao.insertMovie(it.toEntity()) }
                    Result.Success(response.body()!!)
                } else {
                    Result.Error("Error occurred: ${response.message()}")
                }
            }
        } catch (e: HttpException) {
            Result.Error(e.message ?: "HTTP error")
        } catch (e: NetworkException) {
            Result.Error("Network error: ${e.message}")
        } catch (e: UnknownException) {
            Result.Error("Unknown error: ${e.message}")
        } catch (e: Exception) {
            Result.Error("An unexpected error occurred: ${e.message}")
        }
    }
}

