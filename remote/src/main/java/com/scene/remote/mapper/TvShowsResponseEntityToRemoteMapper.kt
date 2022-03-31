package com.scene.remote.mapper

import com.scene.domain.Mapper
import com.scene.domain.entity.TvShowsResponseEntity
import com.scene.remote.response.TvShowsResponse
import com.scene.remote.response.model.TvShow
import javax.inject.Inject

class TvShowsResponseEntityToRemoteMapper @Inject constructor() :
    Mapper<TvShowsResponseEntity, TvShowsResponse> {

    override fun map(input: TvShowsResponseEntity): TvShowsResponse {
        return with(input) {
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
