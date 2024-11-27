package com.example.movieapp.sections_items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.LazyRow
import com.example.movieapp.model.Movie
import com.example.movieapp.view_model.MovieViewModel

@Composable
fun MovieSection(title: String, movies: List<Movie>, navController: NavController, viewModel: MovieViewModel, isHorizontal: Boolean = true) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        if (isHorizontal) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(movies.size) { index ->
                    MovieItem(
                        movie = movies[index],
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(movies.size) { index ->
                    MovieItem(
                        movie = movies[index],
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

