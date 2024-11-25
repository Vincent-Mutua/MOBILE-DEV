package com.example.movieapp.sections_items


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieapp.model.Series
import com.example.movieapp.view_model.SeriesViewModel

@Composable
fun SeriesSection(title: String, series: List<Series>, navController: NavController, viewModel: SeriesViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(series.size) { index ->
                SeriesItem(
                    series = series[index],
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}
