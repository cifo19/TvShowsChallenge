package com.demo.tvshows.remote.response

import com.demo.tvshows.remote.response.model.TvShow
import com.google.gson.annotations.SerializedName

data class TvShowsResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val tvShows: List<TvShow>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
) : BaseResponse()