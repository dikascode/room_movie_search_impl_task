package com.dikascode.moviesearch.data.network

import com.dikascode.moviesearch.data.model.MovieDetailResponse
import com.dikascode.moviesearch.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDbApiService {
    @GET("/")
    suspend fun searchMovies(@Query("s") search: String, @Query("apikey") apiKey: String): MovieResponse

    @GET("/")
    suspend fun getMovieDetails(@Query("i") id: String, @Query("apikey") apiKey: String): MovieDetailResponse
}