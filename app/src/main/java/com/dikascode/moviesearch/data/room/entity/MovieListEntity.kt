package com.dikascode.moviesearch.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_list")
data class MovieListEntity(
    @PrimaryKey val imdbID: String,
    val title: String,
    val year: String,
    val type: String,
    val poster: String
)