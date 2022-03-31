package com.scene.home.presentation.adapteritem

import com.scene.util.AdapterItem

data class TvShowAdapterItem(
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String,
    val voteAverage: String
) : AdapterItem
