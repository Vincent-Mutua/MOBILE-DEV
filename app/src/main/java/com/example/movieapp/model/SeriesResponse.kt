package com.example.movieapp.model


data class SeriesResponse(
    val results: List<Series>
)

data class Series(
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


fun Series.getPosterUrl():
        String = "https://image.tmdb.org/t/p/w500$posterPath"
