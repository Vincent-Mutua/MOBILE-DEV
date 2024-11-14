package com.example.movieapp.model


import com.example.movieapp.model.MediaItem.Series
import com.google.gson.annotations.SerializedName

data class SeriesResponse(
    @SerializedName("page")
    val page: Int, // current page of results

    @SerializedName("results")
    val series: List<Series>, // list of series in the response

    @SerializedName("total_results")
    val totalResults: Int, // total number of results for the query

    @SerializedName("total_pages")
    val totalPages: Int // total number of pages for the query
)
