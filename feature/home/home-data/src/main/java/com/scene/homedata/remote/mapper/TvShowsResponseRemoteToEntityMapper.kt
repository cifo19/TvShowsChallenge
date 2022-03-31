package com.scene.homedata.remote.mapper

import com.scene.homedata.remote.response.TvShowsResponse
import com.scene.homedomain.entity.TvShowEntity
import com.scene.homedomain.entity.TvShowsResponseEntity
import javax.inject.Inject

class TvShowsResponseRemoteToEntityMapper @Inject constructor() {

    fun map(input: TvShowsResponse): TvShowsResponseEntity {
        return with(input) {
            val tvShowEntities = tvShows.map {
                TvShowEntity(
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
            TvShowsResponseEntity(page, tvShowEntities, totalPages, totalResults)
        }
    }
}
