package com.example.movieapp.view_model

// MovieViewModel.kt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.items.HistoryItem
import com.example.movieapp.model.Movie
import com.example.movieapp.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _nowPlaying = MutableStateFlow<List<Movie>>(emptyList())
    val nowPlayingMovies: StateFlow<List<Movie>> = _nowPlaying

    private val _upcoming = MutableStateFlow<List<Movie>>(emptyList())
    val upcomingMovies: StateFlow<List<Movie>> = _upcoming

    private val _topRated = MutableStateFlow<List<Movie>>(emptyList())
    val topRatedMovies: StateFlow<List<Movie>> = _topRated

    private val _popular = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies: StateFlow<List<Movie>> = _popular

    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults

    private val _history = MutableStateFlow<List<HistoryItem>>(emptyList())
    val history: StateFlow<List<HistoryItem>> = _history.asStateFlow()



    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            _nowPlaying.value = repository.getNowPlayingMovies().results
            _upcoming.value = repository.getUpcomingMovies().results
            _topRated.value = repository.getTopRatedMovies().results
            _popular.value = repository.getPopularMovies().results
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            _searchResults.value = repository.searchMovies(query).results
        }

    }

    fun addToHistory(item: HistoryItem) {
        _history.value += item
    }

    suspend fun getMovieById(movieId: String?): Movie? {
        return movieId?.let {
            repository.getMovieById(it)
        }
    }

}
