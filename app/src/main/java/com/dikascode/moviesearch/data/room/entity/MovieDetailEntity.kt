package com.dikascode.moviesearch.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dikascode.moviesearch.data.room.RatingListConverter

@Entity(tableName = "movie_details")
@TypeConverters(RatingListConverter::class)
data class MovieDetailEntity(
    @PrimaryKey
    val imdbID: String,
    val title: String,
    val year: String,
    val rated: String,
    val released: String,
    val runtime: String,
    val genre: String,
    val director: String,
    val writer: String,
    val actors: String,
    val plot: String,
    val language: String,
    val country: String,
    val awards: String,
    val poster: String,
    val ratings: List<Rating>,
    val metascore: String,
    val imdbRating: String,
    val imdbVotes: String,
    val type: String,
    val boxOffice: String,
    val production: String,
    val website: String,
    val response: String
)

data class Rating(
    val source: String,
    val value: String
)
