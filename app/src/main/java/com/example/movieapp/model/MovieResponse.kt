package com.example.movieapp.model

// MovieResponse.kt
data class MovieResponse(
    val results: List<Movie>
)

data class Movie(
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int
)


fun Movie.getPosterUrl():
        String = "https://image.tmdb.org/t/p/w500$posterPath"
