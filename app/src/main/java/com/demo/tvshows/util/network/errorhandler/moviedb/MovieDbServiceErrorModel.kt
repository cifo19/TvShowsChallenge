package com.demo.tvshows.util.network.errorhandler.moviedb

import com.google.gson.annotations.SerializedName

data class MovieDbServiceErrorModel constructor(
    @SerializedName("status_message") val statusMessage: String?,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("success") val success: Boolean
)
