package com.scene.homedata.remote.mapper

import com.scene.homedata.remote.response.TvShow
import com.scene.homedata.remote.response.TvShowsResponse
import com.scene.homedomain.entity.TvShowsResponseEntity
import javax.inject.Inject

class TvShowsResponseEntityToRemoteMapper @Inject constructor() {

    fun map(tvShowsResponseEntity: TvShowsResponseEntity): TvShowsResponse {
        return with(tvShowsResponseEntity) {
            val tvShows = tvShowEntities.map {
                TvShow(
                    it.backdropPath,
                    it.firstAirDate,
                    it.genreIds,
                    it.id,
                    it.name,
                    it.originCountry,
                    it.originalLanguage,
                    it.originalName,
                    it.overview,
                    it.popularity,
                    it.posterPath,
                    it.voteAverage,
                    it.voteCount
                )
            }
            TvShowsResponse(page, tvShows, totalPages, totalResults)
        }
    }
}
