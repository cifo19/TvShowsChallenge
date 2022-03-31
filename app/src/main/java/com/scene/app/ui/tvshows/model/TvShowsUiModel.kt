package com.scene.app.ui.tvshows.model

data class TvShowsUiModel(
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String,
    val voteAverage: Double
)
