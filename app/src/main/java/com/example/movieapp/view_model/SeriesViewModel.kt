package com.example.movieapp.view_model

// MovieViewModel.kt
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.items.HistoryItem
import com.example.movieapp.model.Movie
import com.example.movieapp.model.Series
import com.example.movieapp.repository.SeriesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class SeriesViewModel(private val repository: SeriesRepository) : ViewModel() {

    private val _topRated = MutableStateFlow<List<Series>>(emptyList())
    val topRated: StateFlow<List<Series>> = _topRated

    private val _popular = MutableStateFlow<List<Series>>(emptyList())
    val popular: StateFlow<List<Series>> = _popular

    private val _trendingSeries = MutableStateFlow<List<Series>>(emptyList())
    val trendingSeries: StateFlow<List<Series>> = _trendingSeries

    private val _searchSeries = MutableStateFlow<List<Series>>(emptyList())
    val searchSeries: StateFlow<List<Series>> = _searchSeries



    init {
        fetchSeries()
        fetchTrendingSeries()
    }

    private fun fetchSeries() {
        viewModelScope.launch {
            try {
                _topRated.value = repository.getTopRatedSeries().results ?: emptyList()
                _popular.value = repository.getPopularSeries().results ?: emptyList()
                _trendingSeries.value = repository.getTrendingSeries().results ?: emptyList()
            } catch (e: Exception) {
                Log.e("SeriesViewModel", "Error fetching series: ${e.message}")
            }
        }
    }

    private fun fetchTrendingSeries() {
        viewModelScope.launch {
            try {
                _trendingSeries.value = repository.getTrendingSeries().results ?: emptyList()
            } catch (e: Exception) {
                Log.e("SeriesViewModel", "Error fetching trending series: ${e.message}")
            }
        }
    }

     fun searchSeries(query: String) {
        viewModelScope.launch {
            try {
                _searchSeries.value = repository.searchSeries(query).results ?: emptyList()
            } catch (e: Exception) {
                Log.e("SeriesViewModel", "Error searching series: ${e.message}")
            }
        }
    }
    suspend fun fetchSeriesDetails(seriesId: Int): Result<Series> {
        return try {
            val series = repository.getSeriesDetails(seriesId)
            Result.success(series)
        } catch (e: Exception) {
            Log.e("SeriesViewModel", "Error fetching series details: ${e.message}")
            Result.failure(e)
        }
    }




}
