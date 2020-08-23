package com.demo.tvshows.ui.tvshows.mapper

import com.demo.tvshows.response.model.TvShow
import com.demo.tvshows.ui.tvshows.TvShowsListAdapter.AdapterItem.TvShowAdapterItem
import javax.inject.Inject

class TvShowAdapterItemMapper @Inject constructor() {

    fun map(tvShows: List<TvShow>): List<TvShowAdapterItem> {
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
