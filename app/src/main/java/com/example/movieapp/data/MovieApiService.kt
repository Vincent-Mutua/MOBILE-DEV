package com.example.movieapp.data

import com.example.movieapp.model.MovieResponse
import com.example.movieapp.model.SeriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("0c3e719077ee431286221fe2659bceeb") apiKey: String): MovieResponse

    @GET("tv/popular")
    suspend fun getPopularSeries(
        @Query("0c3e719077ee431286221fe2659bceeb") apiKey: String,
        @Query("page") page: Int
    ): SeriesResponse
}
