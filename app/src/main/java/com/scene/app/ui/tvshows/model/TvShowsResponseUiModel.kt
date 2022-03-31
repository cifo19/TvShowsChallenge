package com.scene.app.ui.tvshows.model

import androidx.room.ColumnInfo

data class TvShowsResponseUiModel(
    @ColumnInfo(name = "results") val tvShows: List<TvShowsUiModel>,
    @ColumnInfo(name = "has_next_page") val hasNextPage: Int
)
