package com.dikascode.moviesearch.data.room

import androidx.room.TypeConverter
import com.dikascode.moviesearch.data.room.entity.Rating
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RatingListConverter {
    @TypeConverter
    fun fromRatingList(ratings: List<Rating>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Rating>>() {}.type
        return gson.toJson(ratings, type)
    }

    @TypeConverter
    fun toRatingList(ratingsString: String): List<Rating> {
        val gson = Gson()
        val type = object : TypeToken<List<Rating>>() {}.type
        return gson.fromJson(ratingsString, type)
    }
}