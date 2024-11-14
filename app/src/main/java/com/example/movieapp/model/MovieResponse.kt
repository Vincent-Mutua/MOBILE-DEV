package com.example.movieapp.model

import com.example.movieapp.model.MediaItem.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    val page: Int, // current page of results

    @SerializedName("results")
    val movies: List<Movie>, // list of movies in the response

    @SerializedName("total_results")
    val totalResults: Int, // total number of results for the query

    @SerializedName("total_pages")
    val totalPages: Int // total number of pages for the query
)
