package com.scene.app.ui.tvshows.adapteritem

import com.scene.app.util.AdapterItem

data class TvShowAdapterItem(
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String,
    val voteAverage: String
) : AdapterItem
