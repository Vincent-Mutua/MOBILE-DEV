package com.example.movieapp.repository



import com.example.movieapp.model.Series
import com.example.movieapp.tmdb.NetworkClient

// SeriesRepository.kt
class SeriesRepository{
    private val service = NetworkClient.tmdbService
    suspend fun getTopRatedSeries() = service.getTopRatedSeries()
    suspend fun getPopularSeries() = service.getPopularSeries()
    suspend fun getSeriesById(seriesId: String?): Series? {
        return if (seriesId != null) {
            service.getSeriesDetails(seriesId.toInt())
        } else {
            null
        }
    }

    suspend fun searchSeries(query: String) = service.searchSeries(query)
}
