# MOBILE-DEV

# Mobile-Dev - Movie Recommendation App

Mobile-Dev is a Kotlin-based movie and TV show recommendation app that helps users find movies and TV shows to watch based on their preferences. It integrates with the TMDb API to provide up-to-date content on movies, TV shows, and more.

## Features

- **Home Screen**: Displays previews of other pages like 'My List,' 'Recommendations,' 'Settings,' and 'Search' through fragments and activities.
- **Movie Recommendations**: Get personalized recommendations based on the user's watch history or preferences.
- **Search Functionality**: Search for movies and TV shows using keywords.
- **My List**: Users can create a personalized list of favorite movies and TV shows.
- **Settings**: Customize the app's functionality, such as theme settings or notification preferences.

## Technologies Used

- **Kotlin**: The primary programming language for the app.
- **TMDb API**: Provides movie and TV show data.
- **Fragments and Activities**: Used to manage the app's user interface.
- **ViewModel & LiveData**: For managing UI-related data in a lifecycle-conscious way.
- **Retrofit**: For API calls to fetch movie and TV show data.
- **Glide/Picasso**: For image loading and caching.
- **Room Database**: For saving user data like My List and preferences locally.

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
        implementation "com.squareup.retrofit2:retrofit:2.9.0"
        implementation "com.squareup.retrofit2:converter-gson:2.9.0"
        implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1"
        implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"
        implementation "com.github.bumptech.glide:glide:4.11.0"
        implementation "androidx.room:room-runtime:2.3.0"
        annotationProcessor "androidx.room:room-compiler:2.3.0"
    }
    ```

3. **API Key**:
   
   Sign up for a TMDb API key at [TMDb](https://www.themoviedb.org/). Then, add the key in your app's `strings.xml` file:

    ```xml
    <string name="tmdb_api_key">YOUR_API_KEY</string>
    ```

4. **Run the app**:

   Open the project in Android Studio, build, and run the app on an emulator or real device.

## Contributing

If you want to contribute to the project, feel free to fork the repository, make changes, and create a pull request. We welcome any contributions to improve the app!

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements

- [TMDb API](https://www.themoviedb.org/) for providing movie and TV show data.
- [Kotlin](https://kotlinlang.org/) for the programming language.
- [Android](https://developer.android.com/) for the platform and libraries.

