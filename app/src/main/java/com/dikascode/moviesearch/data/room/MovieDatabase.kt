package com.dikascode.moviesearch.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dikascode.moviesearch.data.repository.MovieDao

@Database(entities = [MovieEntity::class], version = 1)
@TypeConverters(RatingListConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}