package com.example.movieapp.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Movie
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

    private val _trendingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val trendingMovies: StateFlow<List<Movie>> = _trendingMovies

    private val _trendingSeries = MutableStateFlow<List<Series>>(emptyList())
    val trendingSeries: StateFlow<List<Series>> = _trendingSeries


    init {
        fetchTrending()
    }

    private fun fetchTrending() {
        viewModelScope.launch {
            _trendingMovies.value = movieRepository.getTrendingMovies().results
            _trendingSeries.value = seriesRepository.getTrendingSeries().results
        }
    }

}
