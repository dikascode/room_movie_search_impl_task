package com.dikascode.moviesearch.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dikascode.moviesearch.data.room.entity.MovieDetailEntity
import com.dikascode.moviesearch.data.room.entity.MovieListEntity

@Database(entities = [MovieDetailEntity::class, MovieListEntity::class], version = 2)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS `movie_list` (
                        `imdbID` TEXT NOT NULL, 
                        `title` TEXT NOT NULL, 
                        `year` TEXT NOT NULL, 
                        `type` TEXT NOT NULL, 
                        `poster` TEXT NOT NULL, 
                        PRIMARY KEY(`imdbID`)
                    )
                """)
            }
        }
    }
}
