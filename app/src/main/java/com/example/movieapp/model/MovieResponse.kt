package com.example.movieapp.model

// MovieResponse.kt
data class MovieResponse(
    val results: List<Movie>
)

data class Movie(
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
)


fun Movie.getPosterUrl(): String = "https://image.tmdb.org/t/p/w500${poster_path ?: ""}"

