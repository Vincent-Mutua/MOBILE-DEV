package com.example.movieapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.movieapp.sections_items.MovieSection
import com.example.movieapp.sections_items.SeriesSection
import com.example.movieapp.view_model.MovieViewModel
import com.example.movieapp.view_model.SeriesViewModel
import coil.request.ImageRequest
import com.example.movieapp.model.getPosterUrl


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    movieViewModel: MovieViewModel = viewModel(),
    seriesViewModel: SeriesViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val trendingMovies by movieViewModel.trendingMovies.collectAsState()
    val trendingSeries by seriesViewModel.trendingSeries.collectAsState()
    val searchMovies by movieViewModel.searchMovies.collectAsState()
    val searchSeries by seriesViewModel.searchSeries.collectAsState()

    LaunchedEffect(searchQuery) {
        movieViewModel.searchMovies(searchQuery)
        seriesViewModel.searchSeries(searchQuery)
    }

    Scaffold(
        topBar = { /* No top app bar */ },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                // Custom Search Bar
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent, // Background when focused
                            unfocusedContainerColor = Color.Transparent, // Background when not focused
                            cursorColor = MaterialTheme.colorScheme.primary, // Cursor color
                            focusedIndicatorColor = Color.Transparent, // No underline when focused
                            unfocusedIndicatorColor = Color.Transparent // No underline when not focused
                        )
                    )
                }


                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()
                ) {
                    if (searchQuery.isEmpty()) {
                        MovieSection(
                            title = "Trending Movies",
                            movies = trendingMovies,
                            navController = navController,
                            viewModel= movieViewModel,
                            isHorizontal = true
                        )
                        SeriesSection(
                            title = "Trending Series",
                            series = trendingSeries,
                            navController = navController,
                            viewModel= seriesViewModel,
                            isHorizontal = true
                        )
                    } else {
                        val combinedSearchResults = searchMovies.map { SearchResult(it.id.toString(), it.title, it.getPosterUrl(), isMovie = true) } +
                                searchSeries.map { SearchResult(it.id.toString(), it.name, it.getPosterUrl(), isMovie = false) }

                        if (combinedSearchResults.isEmpty()) {
                            Text("No results found for \"$searchQuery\"", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(8.dp))
                        } else {
                            SearchResultsSection(
                                searchResults = combinedSearchResults,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    )
}


data class SearchResult(val id: String, val title: String, val poster_path: String, val isMovie: Boolean)


@Composable
fun SearchResultsSection(searchResults: List<SearchResult>, navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        searchResults.forEach { result ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (result.isMovie) {
                            navController.navigate("movie_detail/${result.id}")
                        } else {
                            navController.navigate("series_detail/${result.id}")
                        }
                    }
                    .padding(8.dp)
            ) {
                Image(
                    painter = // No placeholder or error image
                    rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(data = result.poster_path)
                            .apply<ImageRequest.Builder>(block = fun ImageRequest.Builder.() {
                                crossfade(true)
                                // No placeholder or error image
                            }).build()
                    ),
                    contentDescription = result.title,
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = result.title, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}



