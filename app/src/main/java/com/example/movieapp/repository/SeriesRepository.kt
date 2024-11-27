package com.example.movieapp.repository



import android.util.Log
import com.example.movieapp.tmdb.NetworkClient
import com.example.movieapp.model.SeriesResponse

class SeriesRepository {
    private val service = NetworkClient.tmdbService

    suspend fun getPopularSeries(): SeriesResponse = logAndFetch { service.getPopularSeries() }
    suspend fun getTopRatedSeries(): SeriesResponse = logAndFetch { service.getTopRatedSeries() }
    suspend fun searchSeries(query: String): SeriesResponse = logAndFetch { service.searchSeries(query) }
    suspend fun getTrendingSeries(): SeriesResponse = logAndFetch { service.getTrendingSeries() }
    suspend fun getSeriesDetails(seriesId: Int) = logAndFetch { service.getSeriesDetails(seriesId) }

    private suspend fun <T> logAndFetch(apiCall: suspend () -> T): T {
        try {
            val response = apiCall()
            Log.d("SeriesRepository", "API call successful: $response")
            return response
        } catch (e: Exception) {
            Log.e("SeriesRepository", "API call failed: ${e.message}")
            throw e
        }
    }
}
