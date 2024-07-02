package com.dikascode.moviesearch

import android.app.Application
import androidx.room.Room
import com.dikascode.moviesearch.data.room.MovieDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieSearchApp: Application() {
    lateinit var database: MovieDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            MovieDatabase::class.java, "movie-database"
        )
            .addMigrations(MovieDatabase.MIGRATION_1_2)
            .build()
    }
}