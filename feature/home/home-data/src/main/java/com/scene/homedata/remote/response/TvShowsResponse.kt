package com.scene.homedata.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowsResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val tvShows: List<TvShow>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
) : BaseResponse()
