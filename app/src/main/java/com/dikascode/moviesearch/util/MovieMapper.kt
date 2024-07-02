package com.dikascode.moviesearch.util

import com.dikascode.moviesearch.data.model.MovieDetailResponse
import com.dikascode.moviesearch.data.room.MovieEntity

import com.dikascode.moviesearch.data.model.Rating as ModelRating
import com.dikascode.moviesearch.data.room.Rating as RoomRating

fun MovieEntity.toMovieDetailResponse(): MovieDetailResponse {
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
        dvd = this.dvd,
        boxOffice = this.boxOffice,
        production = this.production,
        website = this.website,
        response = this.response
    )
}

fun MovieDetailResponse.toEntity(): MovieEntity {
    return MovieEntity(
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
        dvd = this.dvd,
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