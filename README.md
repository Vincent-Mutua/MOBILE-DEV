# MOBILE-DEV

# MovieApp - Movie Recommendation App

MovieApp is a Kotlin-based movie and TV show recommendation app that helps users find movies and TV shows to watch based on their preferences. It integrates with the TMDb API to provide up-to-date content on movies and TV shows.

## Features

- **Home Screen**: Displays previews of other pages like 'Trending Movies,' 'Trending Series,' 'Popular Movies' through composable functions. 
- **Movie Recommendations**: Get personalized recommendations based on the user's watch history or preferences.
- **Search Functionality**: Search for movies and TV shows using keywords.
- **Bottom Navigation bar**: Integrated with navigation functionality to other screens like "Movies", "Series" and "Profile".
- **Settings**: View and edit user profile information.

## Technologies Used

- **Kotlin**: The primary programming language for the app.
- **TMDb API**: Provides movie and TV show data.
- **Composable functions**: Used to manage the app's user interface.
- **ViewModel & LiveData**: For managing UI-related data in a lifecycle-conscious way.
- **Retrofit**: For API calls to fetch movie and TV show data.
- **Glide/Picasso**: For image loading and caching.
- **Firebase**: For setting up user authentication
- **Firestore**: For storing user data. 

## Setup Instructions

1. **Clone the repository**:

    ```bash
    git clone https://github.com/Vincent-Mutua/mobile-dev.git
    cd mobile-dev
    ```

2. **Add dependencies**:

    Make sure you have the necessary dependencies in your `build.gradle` files:

    ```gradle
    dependencies {
   implementation(libs.androidx.navigation.testing)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.glide)
    annotationProcessor(libs.compiler)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.logging.interceptor)
    implementation(platform(libs.firebase.bom))
    implementation(libs.google.firebase.auth)
    implementation(libs.google.firebase.firestore)
    implementation (libs.firebase.storage.ktx)
    implementation (libs.coil.compose)
    implementation (libs.androidx.material.icons.extended)
    implementation (libs.androidx.activity.compose.v170)
    
    }
    ```

3. **API Key**:
   
   Sign up for a TMDb API key at [TMDb](https://www.themoviedb.org/). Then, add the key in your app's `strings.xml` file:

    ```xml
    <string name="tmdb_api_key">YOUR_API_KEY</string>
    ```

4. **Run the app**:

   Open the project in Android Studio, build, and run the app on an emulator or real device.


## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements

- [TMDb API](https://www.themoviedb.org/) for providing movie and TV show data.
- [Kotlin](https://kotlinlang.org/) for the programming language.
- [Android](https://developer.android.com/) for the platform and libraries.

