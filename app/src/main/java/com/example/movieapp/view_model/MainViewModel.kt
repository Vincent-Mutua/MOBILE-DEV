package com.example.movieapp.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.items.HistoryItem
import com.example.movieapp.model.Series
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.repository.SeriesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository
) : ViewModel() {

    private val _upcomingSchedule = MutableStateFlow<List<Series>>(emptyList())
    val upcomingSchedule: StateFlow<List<Series>> = _upcomingSchedule

    private val _history = MutableStateFlow<List<HistoryItem>>(emptyList())
    val history: StateFlow<List<HistoryItem>> = _history

    init {
        fetchSchedule()
    }

    private fun fetchSchedule() {
        viewModelScope.launch {
            _upcomingSchedule.value = seriesRepository.getPopularSeries().results

        }
    }

    fun addToHistory(item: HistoryItem) {
        val updatedHistory = _history.value.toMutableList()
        updatedHistory.add(item)
        _history.value = updatedHistory
    }
}


