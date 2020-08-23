package com.demo.tvshows.mapper

import com.demo.tvshows.entity.TvShow
import com.demo.tvshows.response.TvShowsResponse
import javax.inject.Inject

class TvShowEntityMapper @Inject constructor() {
    @OptIn(ExperimentalStdlibApi::class)
    fun map(tvShowsResponse: TvShowsResponse): List<TvShow> = buildList {
        val tvshows = tvShowsResponse.tvShows.map { tvShow ->
            with(tvShow) {
                TvShow(
                    id,
                    backdropPath,
                    firstAirDate,
                    genreIds,
                    name,
                    originCountry,
                    originalLanguage,
                    originalName,
                    overview,
                    popularity,
                    posterPath,
                    voteAverage,
                    voteCount
                )
            }
        }
        addAll(tvshows)
    }
}
