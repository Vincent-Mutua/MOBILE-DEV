package com.example.movieapp.repository


import com.example.movieapp.model.Movie
import com.example.movieapp.tmdb.NetworkClient

// MovieRepository.kt
class MovieRepository {
    private val service = NetworkClient.tmdbService
    suspend fun getNowPlayingMovies() = service.getNowPlayingMovies()
    suspend fun getUpcomingMovies() = service.getUpcomingMovies()
    suspend fun getTopRatedMovies() = service.getTopRatedMovies()
    suspend fun getPopularMovies() = service.getPopularMovies()
    suspend fun searchMovies(query: String) = service.searchMovies(query)
    suspend fun getMovieById(movieId: String?): Movie? {
        return if (movieId != null) {
            service.getMovieDetails(movieId.toInt())
        } else {
            null
        }
    }



}
