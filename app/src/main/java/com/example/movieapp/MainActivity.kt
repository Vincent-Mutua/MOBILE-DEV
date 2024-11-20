package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Splashscreen imports
import kotlinx.coroutines.delay
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color


import com.example.movieapp.ui.theme.MovieAppTheme

// Preview
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.movieapp.auth.LoginScreen
import com.example.movieapp.auth.RegisterScreen


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme{
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController= rememberNavController()
                    NavHost(navController=navController, startDestination = "splash_screen"){
                        composable("splash_screen"){ SplashScreen(navController) }
                        composable("register_screen"){ RegisterScreen(navController) }
                        composable("login_screen"){ LoginScreen(navController) }

                    }
                }
            }
        }
    }
}


@Composable
fun SplashScreen(navController: NavHostController) {

    LaunchedEffect(Unit) {
        delay(2000) // 2 seconds
        navController.navigate("register_screen")

    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color=Color(0xFFBCBAAE)),
            contentAlignment = Alignment.Center

    ) {
        Image( painter = painterResource(id = R.drawable.binge_buddy_transparent),
            contentDescription = "SplashScreen Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(250.dp)

        )
        Text(
            text = "Anguka Nayo",
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)

        )
    }

}





@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    SplashScreen(navController = rememberNavController())
}


