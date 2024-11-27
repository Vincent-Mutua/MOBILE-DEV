package com.example.movieapp.repository


import android.util.Log
import com.example.movieapp.tmdb.NetworkClient


class MovieRepository {
    private val service = NetworkClient.tmdbService

    suspend fun getNowPlayingMovies() = logAndFetch { service.getNowPlayingMovies() }
    suspend fun getUpcomingMovies() = logAndFetch { service.getUpcomingMovies() }
    suspend fun getTopRatedMovies() = logAndFetch { service.getTopRatedMovies() }
    suspend fun getPopularMovies() = logAndFetch { service.getPopularMovies() }
    suspend fun searchMovies(query: String) = logAndFetch { service.searchMovies(query) }
    suspend fun getTrendingMovies() = logAndFetch { service.getTrendingMovies() }
    suspend fun getMovieDetails(movieId: Int) = logAndFetch { service.getMovieDetails(movieId) }


    private suspend fun <T> logAndFetch(apiCall: suspend () -> T): T {
        try {
            val response = apiCall()
            Log.d("MovieRepository", "API call is successful: $response")
            return response
        } catch (e: Exception) {
            Log.e("MovieRepository", "API call has failed: ${e.message}")
            throw e
        }
    }
}

