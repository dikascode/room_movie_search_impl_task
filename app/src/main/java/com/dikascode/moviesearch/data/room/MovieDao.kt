package com.dikascode.moviesearch.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dikascode.moviesearch.data.room.entity.MovieDetailEntity
import com.dikascode.moviesearch.data.room.entity.MovieListEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_details WHERE imdbID = :id")
    suspend fun getMovieDetailById(id: String): MovieDetailEntity?

    @Query("SELECT * FROM movie_list WHERE title LIKE '%' || :query || '%'")
    suspend fun searchMovies(query: String): List<MovieListEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movie: MovieDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieLists(movies: List<MovieListEntity>)
}