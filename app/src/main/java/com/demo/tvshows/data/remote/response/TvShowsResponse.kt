package com.demo.tvshows.data.remote.response

import com.demo.tvshows.data.remote.response.model.TvShow
import com.google.gson.annotations.SerializedName

data class TvShowsResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("tvShows") val tvShows: List<TvShow>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
