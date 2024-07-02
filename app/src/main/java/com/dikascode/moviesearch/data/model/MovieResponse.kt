package com.dikascode.moviesearch.data.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Search")
    val search: List<Movie>,

    @SerializedName("totalResults")
    val totalResults: String,

    @SerializedName("Response")
    val response: String
)
