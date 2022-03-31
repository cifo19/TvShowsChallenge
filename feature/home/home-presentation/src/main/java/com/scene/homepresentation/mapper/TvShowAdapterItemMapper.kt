package com.scene.homepresentation.mapper

import com.scene.homedomain.entity.TvShowEntity
import com.scene.homepresentation.adapteritem.TvShowAdapterItem
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
