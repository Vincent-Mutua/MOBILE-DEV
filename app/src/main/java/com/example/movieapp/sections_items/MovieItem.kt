package com.example.movieapp.sections_items


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.items.HistoryItem
import com.example.movieapp.model.Movie
import com.example.movieapp.model.getPosterUrl
import com.example.movieapp.view_model.MovieViewModel

@Composable
fun MovieItem(navController: NavController, movie: Movie, viewModel: MovieViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            navController.navigate("movie_detail/${movie.id}")
            viewModel.addToHistory(
                HistoryItem(
                    id = movie.id.toString(),
                    title = movie.title,
                    posterUrl = movie.posterPath ?: "",
                    rating = movie.voteAverage
                )
            )
        }
    ) {
        Image(
            painter = rememberAsyncImagePainter(movie.getPosterUrl()),
            contentDescription = movie.title,
            modifier = Modifier
                .size(120.dp, 180.dp)
                .padding(bottom = 8.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier.width(120.dp)
        )
    }
}
