package com.demo.tvshows.ui.tvshows.mapper

import com.demo.tvshows.entity.TvShowEntity
import com.demo.tvshows.ui.tvshows.TvShowsListAdapter.AdapterItem.TvShowAdapterItem
import javax.inject.Inject

class TvShowAdapterItemMapper @Inject constructor() {

    fun map(tvShows: List<TvShowEntity>): List<TvShowAdapterItem> {
        return tvShows.map {
            TvShowAdapterItem(
                it.id,
                it.name,
                it.overview,
                it.posterPath,
                it.voteAverage
            )
        }
    }
}
