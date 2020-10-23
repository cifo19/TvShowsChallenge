package com.demo.tvshows.ui.tvshows.adapteritem

import com.demo.tvshows.util.AdapterItem

data class TvShowAdapterItem(
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String,
    val voteAverage: String
) : AdapterItem
