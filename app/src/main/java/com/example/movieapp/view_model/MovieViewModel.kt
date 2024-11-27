package com.example.movieapp.view_model

// MovieViewModel.kt
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Movie
import com.example.movieapp.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _nowPlaying = MutableStateFlow<List<Movie>>(emptyList())
    val nowPlayingMovies: StateFlow<List<Movie>> = _nowPlaying

    private val _upcoming = MutableStateFlow<List<Movie>>(emptyList())
    val upcomingMovies: StateFlow<List<Movie>> = _upcoming

    private val _topRated = MutableStateFlow<List<Movie>>(emptyList())
    val topRatedMovies: StateFlow<List<Movie>> = _topRated

    private val _popular = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies: StateFlow<List<Movie>> = _popular

    private val _trendingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val trendingMovies: StateFlow<List<Movie>> = _trendingMovies

    private val _searchMovies = MutableStateFlow<List<Movie>>(emptyList())
    val searchMovies: StateFlow<List<Movie>> = _searchMovies


    init {
        fetchMovies()
        fetchTrendingMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            try {
                _nowPlaying.value = repository.getNowPlayingMovies().results ?: emptyList()
                _upcoming.value = repository.getUpcomingMovies().results ?: emptyList()
                _topRated.value = repository.getTopRatedMovies().results ?: emptyList()
                _popular.value = repository.getPopularMovies().results ?: emptyList()
                _trendingMovies.value = repository.getTrendingMovies().results ?: emptyList()
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching movies: ${e.message}")
            }
        }
    }

    private fun fetchTrendingMovies() {
        viewModelScope.launch {
            try {
                _trendingMovies.value = repository.getTrendingMovies().results ?: emptyList()
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching trending movies: ${e.message}")
            }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                _searchMovies.value = repository.searchMovies(query).results ?: emptyList()
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error searching movies: ${e.message}")
            }
        }
    }

    suspend fun fetchMovieDetails(movieId: Int): Result<Movie> {
        return try {
            val movie = repository.getMovieDetails(movieId)
            Result.success(movie)
        } catch (e: Exception) {
            Log.e("MovieViewModel", "Error fetching movie details: ${e.message}")
            Result.failure(e)
        }
    }



}
