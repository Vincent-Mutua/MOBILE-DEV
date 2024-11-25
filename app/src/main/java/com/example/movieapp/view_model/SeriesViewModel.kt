package com.example.movieapp.view_model

// MovieViewModel.kt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.items.HistoryItem
import com.example.movieapp.model.Series
import com.example.movieapp.repository.SeriesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow

class SeriesViewModel(private val repository: SeriesRepository) : ViewModel() {


    private val _history = MutableStateFlow<List<HistoryItem>>(emptyList())
    val history: StateFlow<List<HistoryItem>> = _history.asStateFlow()


    private val _topRated = MutableStateFlow<List<Series>>(emptyList())
    val topRated: StateFlow<List<Series>> = _topRated

    private val _popular = MutableStateFlow<List<Series>>(emptyList())
    val popular: StateFlow<List<Series>> = _popular

    private val _searchResults = MutableStateFlow<List<Series>>(emptyList())
    val searchResults: StateFlow<List<Series>> = _searchResults


    init {
        fetchSeries()
    }

    private fun fetchSeries() {
        viewModelScope.launch {
            _topRated.value = repository.getTopRatedSeries().results
            _popular.value = repository.getPopularSeries().results
        }
    }

    fun searchSeries(query: String) {
        viewModelScope.launch {
            _searchResults.value = repository.searchSeries(query).results
        }

    }


    fun addToHistory(item: HistoryItem) {
        _history.value += item
    }

    fun getSeriesById(seriesId: Int): Series {
        return _popular.value.find { it.id == seriesId }
            ?: _topRated.value.find { it.id == seriesId }
            ?: throw IllegalStateException("Series not found")
    }
    suspend fun getSeriesById(seriesId: String?): Series? {
        return seriesId?.let {
            repository.getSeriesById(it)
        }
    }


}
