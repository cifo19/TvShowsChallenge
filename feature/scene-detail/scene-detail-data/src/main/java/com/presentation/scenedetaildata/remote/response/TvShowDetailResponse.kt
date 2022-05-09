package com.presentation.scenedetaildata.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowDetailResponse(
    @SerializedName("name") val name: String
)
