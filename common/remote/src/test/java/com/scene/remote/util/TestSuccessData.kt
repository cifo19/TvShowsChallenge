package com.scene.remote.util

import com.google.gson.annotations.SerializedName

data class TestSuccessData(
    @SerializedName("name") val name: String,
    @SerializedName("surname") val surname: String,
    @SerializedName("age") val age: Int
)
