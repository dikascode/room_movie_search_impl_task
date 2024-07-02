package com.dikascode.moviesearch.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dikascode.moviesearch.data.room.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies WHERE imdbID = :id")
    suspend fun getMovieById(id: String): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)
}