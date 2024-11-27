package com.example.movieapp.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieapp.sections_items.MovieSection
import com.example.movieapp.view_model.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(navController: NavController, viewModel: MovieViewModel = viewModel()) {
    val nowPlayingMovies by viewModel.nowPlayingMovies.collectAsState()
    val upcomingMovies by viewModel.upcomingMovies.collectAsState()
    val topRatedMovies by viewModel.topRatedMovies.collectAsState()
    val popularMovies by viewModel.popularMovies.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Now Playing", "Upcoming", "Trending", "Popular")

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = { Text("Movies") },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = Color.White
                    )
                )
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    edgePadding = 0.dp,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    tabs.forEachIndexed { index, tab ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(tab, color = Color.White) }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            when (selectedTab) {
                0 -> MovieSection(title = "Now Playing", movies = nowPlayingMovies, navController = navController, viewModel = viewModel)
                1 -> MovieSection(title = "Upcoming", movies = upcomingMovies, navController = navController, viewModel = viewModel)
                2 -> MovieSection(title = "Trending", movies = topRatedMovies, navController = navController, viewModel = viewModel)
                3 -> MovieSection(title = "Popular", movies = popularMovies, navController = navController, viewModel = viewModel)
            }
        }
    }
}

