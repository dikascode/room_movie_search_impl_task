package com.dikascode.moviesearch.util

import com.dikascode.moviesearch.data.model.MovieDetailResponse
import com.dikascode.moviesearch.data.room.entity.MovieDetailEntity

import com.dikascode.moviesearch.data.model.Rating as ModelRating
import com.dikascode.moviesearch.data.room.entity.Rating as RoomRating

import com.dikascode.moviesearch.data.model.Movie
import com.dikascode.moviesearch.data.room.entity.MovieListEntity

fun MovieDetailEntity.toMovieDetailResponse(): MovieDetailResponse {
    return MovieDetailResponse(
        imdbID = this.imdbID,
        title = this.title,
        year = this.year,
        rated = this.rated,
        released = this.released,
        runtime = this.runtime,
        genre = this.genre,
        director = this.director,
        writer = this.writer,
        actors = this.actors,
        plot = this.plot,
        language = this.language,
        country = this.country,
        awards = this.awards,
        poster = this.poster,
        ratings = this.ratings.map { it.toModel() },
        metascore = this.metascore,
        imdbRating = this.imdbRating,
        imdbVotes = this.imdbVotes,
        type = this.type,
        boxOffice = this.boxOffice,
        production = this.production,
        website = this.website,
        response = this.response
    )
}

fun MovieDetailResponse.toEntity(): MovieDetailEntity {
    return MovieDetailEntity(
        imdbID = this.imdbID,
        title = this.title,
        year = this.year,
        rated = this.rated,
        released = this.released,
        runtime = this.runtime,
        genre = this.genre,
        director = this.director,
        writer = this.writer,
        actors = this.actors,
        plot = this.plot,
        language = this.language,
        country = this.country,
        awards = this.awards,
        poster = this.poster,
        ratings = this.ratings.map { it.toEntity() },
        metascore = this.metascore,
        imdbRating = this.imdbRating,
        imdbVotes = this.imdbVotes,
        type = this.type,
        boxOffice = this.boxOffice,
        production = this.production,
        website = this.website,
        response = this.response
    )
}

fun ModelRating.toEntity(): RoomRating {
    return RoomRating(
        source = this.source,
        value = this.value
    )
}

fun RoomRating.toModel(): ModelRating {
    return ModelRating(
        source = this.source,
        value = this.value
    )
}

fun Movie.toEntity(): MovieListEntity {
    return MovieListEntity(
        imdbID = this.imdbID,
        title = this.title,
        year = this.year,
        type = this.type,
        poster = this.poster
    )
}

fun MovieListEntity.toMovie(): Movie {
    return Movie(
        imdbID = this.imdbID,
        title = this.title,
        year = this.year,
        type = this.type,
        poster = this.poster
    )
}