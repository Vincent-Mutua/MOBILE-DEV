package com.example.movieapp

import TMDbService
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import com.example.movieapp.auth.LoginScreen
import com.example.movieapp.auth.RegisterScreen
import com.example.movieapp.screens.MainScreen
import com.example.movieapp.ui.theme.MovieAppTheme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Splashscreen imports
import kotlinx.coroutines.delay
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.details.MovieDetailsScreen
import com.example.movieapp.details.SeriesDetailsScreen
import com.example.movieapp.model.Movie
import com.example.movieapp.model.Series
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.repository.SeriesRepository
import com.example.movieapp.view_model.MovieViewModel
import com.example.movieapp.view_model.SeriesViewModel



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    val movieRepository = MovieRepository()
                    val seriesRepository = SeriesRepository()
                    val movieViewModel: MovieViewModel = viewModel(factory = MovieViewModelFactory(movieRepository))
                    val seriesViewModel: SeriesViewModel = viewModel(factory = SeriesViewModelFactory(seriesRepository))

                    NavHost(navController = navController, startDestination = "splash_screen") {
                        composable("splash_screen") { SplashScreen(navController) }
                        composable("register_screen") { RegisterScreen(navController) }
                        composable("login_screen") { LoginScreen(navController) }
                        composable("main_screen") { MainScreen(navController, movieViewModel, seriesViewModel) }

                        composable("movie_detail/{movieId}") { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getString("movieId")
                            var movie by remember { mutableStateOf<Movie?>(null) }
                            LaunchedEffect(movieId) {
                                movie = movieViewModel.getMovieById(movieId)
                            }
                            movie?.let {
                                MovieDetailsScreen(movie = it)
                            }
                        }
                        composable("series_detail/{seriesId}") { backStackEntry ->
                            val seriesId = backStackEntry.arguments?.getString("seriesId")
                            var series by remember { mutableStateOf<Series?>(null) }
                            LaunchedEffect(seriesId) {
                                series = seriesViewModel.getSeriesById(seriesId)
                            }
                            series?.let {
                                SeriesDetailsScreen(series = it)
                            }
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(2000) // 2 seconds
        navController.navigate("main_screen")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFBCBAAE)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.binge_buddy_transparent),
            contentDescription = "SplashScreen Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(250.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    SplashScreen(navController = rememberNavController())
}

class MovieViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class SeriesViewModelFactory(private val repository: SeriesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SeriesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SeriesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
