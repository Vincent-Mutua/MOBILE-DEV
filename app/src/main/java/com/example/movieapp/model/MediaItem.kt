package com.example.movieapp.model

sealed class MediaItem {
    data class Movie(
        val id: Int,
        val title: String,
        val overview: String,
        val poster_path: String,
        val release_date: String,
        val vote_average: Double
    ) : MediaItem()

    data class Series(
        val id: Int,
        val name: String,
        val overview: String,
        val poster_path: String,
        val first_air_date: String,
        val vote_average: Double,
        val number_of_seasons: Int,
        val number_of_episodes: Int
    ) : MediaItem()
}

