package com.demo.tvshows.util.network.errorhandler

import com.google.gson.annotations.SerializedName

data class ErrorModel constructor(
    @SerializedName("status_message") val statusMessage: String?,
    @SerializedName("status_code") val statusCode: Int
)
