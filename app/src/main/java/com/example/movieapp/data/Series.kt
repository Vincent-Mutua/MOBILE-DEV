package com.example.movieapp.data

data class Series (
    val id: String,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String,
    val backdropPath: String?,
    val voteAverage: String,
    val genreIds: List<Int>
)
