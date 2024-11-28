package com.example.movieapp

import ProfileScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.auth.LoginScreen
import com.example.movieapp.auth.RegisterScreen
import com.example.movieapp.details.MovieDetailsScreen
import com.example.movieapp.details.SeriesDetailsScreen
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.repository.SeriesRepository
import com.example.movieapp.screens.MainScreen
import com.example.movieapp.screens.MoviesScreen
import com.example.movieapp.screens.SeriesScreen
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.view_model.MovieViewModel
import com.example.movieapp.view_model.SeriesViewModel
import kotlinx.coroutines.delay
import java.util.Locale


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                val navController = rememberNavController()
                val movieRepository = MovieRepository()
                val seriesRepository = SeriesRepository()
                val movieViewModel: MovieViewModel = viewModel(factory = MovieViewModelFactory(movieRepository))
                val seriesViewModel: SeriesViewModel = viewModel(factory = SeriesViewModelFactory(seriesRepository))

                Scaffold(
                    bottomBar = {
                        val currentBackStackEntry = navController.currentBackStackEntryAsState().value
                        val currentDestination = currentBackStackEntry?.destination?.route
                        if (currentDestination in listOf("main_screen", "movies", "series", "profile_screen")) {
                            BottomNavBar(navController)
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "splash_screen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("splash_screen") { SplashScreen(navController) }
                        composable("register_screen") { RegisterScreen(navController) }
                        composable("login_screen") { LoginScreen(navController) }
                        composable("main_screen") { MainScreen(navController, movieViewModel,seriesViewModel) }
                        composable("movies") { MoviesScreen(navController, movieViewModel) }
                        composable("series") { SeriesScreen(navController, seriesViewModel) }
                        composable("movie_detail/{movieId}") { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
                            movieId?.let {
                                MovieDetailsScreen(movieId = it, movieViewModel = movieViewModel)
                            }
                        }
                        composable("series_detail/{seriesId}") { backStackEntry ->
                            val seriesId = backStackEntry.arguments?.getString("seriesId")?.toIntOrNull()
                            seriesId?.let {
                                SeriesDetailsScreen(seriesId = it, seriesViewModel = seriesViewModel)
                            }
                        }
                        composable("profile_screen") { ProfileScreen(navController) }



                    }
                }
            }
        }
    }
}




@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(5000) // 5 seconds
        navController.navigate("main_screen"){
            popUpTo("splash_screen") { inclusive = true }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFBCBAAE)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Movie App",
            color = Color.Black, // Black text color
            fontSize = 40.sp, // Adjust font size as needed
            fontWeight = FontWeight.Bold, // Bold text
            fontFamily = FontFamily.SansSerif, // Set font family to Verdana
            modifier = Modifier.padding(16.dp) // Optional padding
        )
    }

}


@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf("main_screen", "movies", "series", "profile_screen")
    val icons = listOf(Icons.Default.Home, Icons.Default.Movie, Icons.Default.Tv,Icons.Default.AccountCircle)
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(icons[index], contentDescription = "Bottom nav bar") },
                label = {
                    val label = item.replace("_", " ")
                    Text(
                        label.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                    )
                },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item) { launchSingleTop = true }
                }
            )
        }
    }
}




