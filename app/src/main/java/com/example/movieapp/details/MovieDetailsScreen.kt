package com.example.movieapp.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.model.Movie
import com.example.movieapp.model.getPosterUrl
import com.example.movieapp.view_model.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(movieId: Int, movieViewModel: MovieViewModel) {
    var movie by remember { mutableStateOf<Movie?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showContextMenu by remember { mutableStateOf(false) }


    LaunchedEffect(movieId) {
        val result = movieViewModel.fetchMovieDetails(movieId)
        result.fold(
            onSuccess = { fetchedMovie ->
                movie = fetchedMovie
                errorMessage = null
            },
            onFailure = { error ->
                movie = null
                errorMessage = error.message
            }
        )
    }

    when {
        movie != null -> {
            val movieDetails = movie!!
            Scaffold(
                topBar = {}
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())

                ) {
                    Image(
                        painter = rememberAsyncImagePainter(movieDetails.getPosterUrl()),
                        contentDescription = movieDetails.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Text(
                            text = movieDetails.title,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Text(
                            text = "Original Title: ${movieDetails.original_title}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = "Overview:",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Text(
                            text = movieDetails.overview,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Row {
                            Text(
                                text = "Release Date: ${movieDetails.release_date}",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.height(32.dp).width(8.dp))
                            Text(
                                text = "Rating: ${movieDetails.vote_average}",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = "Votes: ${movieDetails.vote_count}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
        errorMessage != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: $errorMessage", color = Color.Red)
            }
        }
        else -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}
