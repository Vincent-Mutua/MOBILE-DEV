// TMDbService.kt
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieResponse
import com.example.movieapp.model.Series
import com.example.movieapp.model.SeriesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val TMDB_API_KEY = "0c3e719077ee431286221fe2659bceeb" // Replace with your API key


interface TMDbService {
    //Movies
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") apiKey: String = TMDB_API_KEY): MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String = TMDB_API_KEY): MovieResponse

    @GET("trending/movie/day")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String = TMDB_API_KEY): MovieResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String = TMDB_API_KEY): MovieResponse

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String, @Query("api_key") apiKey: String = TMDB_API_KEY): MovieResponse

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: Int, @Query("api_key") apiKey: String = TMDB_API_KEY): Movie

    //TV Shows
    @GET("tv/popular")
    suspend fun getPopularSeries(@Query("api_key") apiKey: String = TMDB_API_KEY): SeriesResponse


    @GET("tv/topRated")
    suspend fun getTopRatedSeries(@Query("api_key") apiKey: String = TMDB_API_KEY): SeriesResponse

    @GET("search/tv")
    suspend fun searchSeries(@Query("query") query: String, @Query("api_key") apiKey: String = TMDB_API_KEY): SeriesResponse


    @GET("tv/{seriesId}")
    suspend fun getSeriesDetails(@Path("seriesId") movieId: Int, @Query("api_key") apiKey: String = TMDB_API_KEY): Series



}
