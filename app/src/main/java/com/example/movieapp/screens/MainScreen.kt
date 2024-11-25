package com.example.movieapp.screens

//Window Insets
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
//Material3
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

//Navigation
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.items.HistoryItem
import com.example.movieapp.model.Movie
import com.example.movieapp.model.Series
import com.example.movieapp.sections_items.MovieSection
import com.example.movieapp.sections_items.SeriesSection

import com.example.movieapp.view_model.MovieViewModel
import com.example.movieapp.view_model.SeriesViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    movieViewModel: MovieViewModel = viewModel(),
    seriesViewModel: SeriesViewModel = viewModel()
) {
    Scaffold(
        topBar = { SearchBar(movieViewModel, seriesViewModel) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).verticalScroll(rememberScrollState())) {
            val movieSearchResults by movieViewModel.searchResults.collectAsState()
            val seriesSearchResults by seriesViewModel.searchResults.collectAsState()

            if (movieSearchResults .isNotEmpty() || seriesSearchResults.isNotEmpty()) {
                MovieSection(
                    title = "Movie Search Results",
                    movies = movieSearchResults ,
                    navController = navController,
                    viewModel = movieViewModel
                )
                SeriesSection(
                    title = "Series Search Results",
                    series = seriesSearchResults,
                    navController = navController,
                    viewModel = seriesViewModel
                )
            } else {
                MovieSection(
                    title = "Upcoming Movies",
                    movies = movieViewModel.upcomingMovies.collectAsState().value,
                    navController = navController,
                    viewModel = movieViewModel
                )
                MovieSection(
                    title = "Popular Movies",
                    movies = movieViewModel.popularMovies.collectAsState().value,
                    navController = navController,
                    viewModel = movieViewModel
                )
                SeriesSection(
                    title = "Popular Series",
                    series = seriesViewModel.popular.collectAsState().value,
                    navController = navController,
                    viewModel = seriesViewModel
                )
               // HistorySection(history = movieViewModel.history.collectAsState().value)
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(movieViewModel: MovieViewModel, seriesViewModel: SeriesViewModel) {
    var textState by remember { mutableStateOf(TextFieldValue()) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        BasicTextField(
            value = textState,
            onValueChange = {
                textState = it
                if (it.text.isNotBlank()) {
                    movieViewModel.searchMovies(it.text)
                    seriesViewModel.searchSeries(it.text)
                }
            },
            decorationBox = { innerTextField ->

            Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .border(1.dp, Color.Black)
                        .height(56.dp)
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(Icons.Default.Search, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        if (textState.text.isEmpty()) {
                            Text(
                                text = "Search MovieBase",
                                color = Color.Black
                            )
                        }
                        innerTextField()
                    }
                }
            }
        )
    }
}


@Composable
fun HistorySection(history: List<HistoryItem>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "History", style = MaterialTheme.typography.titleMedium)
        history.forEach { item ->
            HistoryItemCard(item)
        }
    }
}

@Composable
fun HistoryItemCard(item: HistoryItem) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = item.title, style = MaterialTheme.typography.bodyMedium)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(item.posterUrl),
                contentDescription = item.title,
                modifier = Modifier.size(64.dp, 96.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = "Rating: ${item.rating}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}


@Composable
fun BottomNavBar(navController:NavHostController) {
    val items = listOf("Home", "Profile","Settings")
    val icons = listOf(Icons.Default.Home, Icons.Default.Person, Icons.Default.Settings)
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(icons[index], contentDescription = null) },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = { selectedItem = index
                    navController.navigate(item) { launchSingleTop = true
                    }
                }
            )
        }
    }
}

