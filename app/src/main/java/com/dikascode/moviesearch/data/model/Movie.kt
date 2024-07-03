package com.dikascode.moviesearch.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @SerializedName("Title")
    val title: String,

    @SerializedName("Year")
    val year: String,

    @SerializedName("imdbID")
    val imdbID: String,

    @SerializedName("Type")
    val type: String,

    @SerializedName("Poster")
    val poster: String
): Parcelable
