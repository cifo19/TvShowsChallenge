package com.scene.app.ui.tvshows.mapper

import com.scene.domain.entity.TvShowEntity
import com.scene.app.ui.tvshows.adapteritem.TvShowAdapterItem
import javax.inject.Inject

class TvShowAdapterItemMapper @Inject constructor() {

    fun map(tvShows: List<TvShowEntity>): List<TvShowAdapterItem> {
        return tvShows.map {
            TvShowAdapterItem(
                it.id,
                it.name,
                it.overview,
                it.posterPath,
                it.voteAverage.toString()
            )
        }
    }
}
