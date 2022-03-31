package com.scene.remote.response

import com.scene.remote.response.model.TvShow
import com.google.gson.annotations.SerializedName

data class TvShowsResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val tvShows: List<TvShow>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
) : BaseResponse()
