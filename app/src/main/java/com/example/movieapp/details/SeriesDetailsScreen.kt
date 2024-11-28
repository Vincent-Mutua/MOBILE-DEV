package com.example.movieapp.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.movieapp.model.Series
import com.example.movieapp.model.getPosterUrl
import com.example.movieapp.view_model.SeriesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeriesDetailsScreen(seriesId: Int, seriesViewModel: SeriesViewModel) {
    var series by remember { mutableStateOf<Series?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(seriesId) {
        val result = seriesViewModel.fetchSeriesDetails(seriesId)
        result.fold(
            onSuccess = { fetchedSeries ->
                series = fetchedSeries
                errorMessage = null
            },
            onFailure = { error ->
                series = null
                errorMessage = error.message
            }
        )
    }

    when {
        series != null -> {
            val seriesDetails = series!!
            Scaffold(
                topBar = {}
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(seriesDetails.getPosterUrl()),
                        contentDescription = seriesDetails.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Text(
                            text = seriesDetails.name,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Text(
                            text = "Original Name: ${seriesDetails.original_name}",
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
                            text = seriesDetails.overview,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Row {
                            Text(
                                text = "First Air Date: ${seriesDetails.first_air_date}",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.height(32.dp))

                            Text(
                                text = "Rating: ${seriesDetails.vote_average}",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = "Votes: ${seriesDetails.vote_count}",
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

