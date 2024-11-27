package com.example.movieapp.model


data class SeriesResponse(
    val results: List<Series>
)

data class Series(
    val id: Int,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val poster_path: String?,
    val first_air_date: String,
    val name: String,
    val vote_average: Double,
    val vote_count: Int
)


fun Series.getPosterUrl(): String = "https://image.tmdb.org/t/p/w500${poster_path ?: ""}"

