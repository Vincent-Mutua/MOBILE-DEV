package com.example.movieapp.screens





//Material3
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


//Navigation
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController



// MovieScreen.kt
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.items.HistoryItem
import com.example.movieapp.model.Movie
import com.example.movieapp.model.getPosterUrl
import com.example.movieapp.sections_items.MovieSection
import com.example.movieapp.view_model.MainViewModel
import com.example.movieapp.view_model.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(navController: NavController, viewModel: MovieViewModel = viewModel()) {
    val nowPlayingMovies by viewModel.nowPlayingMovies.collectAsState()
    val upcomingMovies by viewModel.upcomingMovies.collectAsState()
    val topRatedMovies by viewModel.topRatedMovies.collectAsState()
    val popularMovies by viewModel.popularMovies.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Movies") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            MovieSection(title = "Now Playing", movies = nowPlayingMovies, navController = navController, viewModel = viewModel)
            MovieSection(title = "Upcoming", movies = upcomingMovies, navController = navController, viewModel = viewModel)
            MovieSection(title = "Top Rated", movies = topRatedMovies, navController = navController, viewModel = viewModel)
            MovieSection(title = "Popular", movies = popularMovies, navController = navController, viewModel = viewModel)
        }
    }
}


