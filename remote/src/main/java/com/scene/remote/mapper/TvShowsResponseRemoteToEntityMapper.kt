package com.scene.remote.mapper

import com.scene.domain.Mapper
import com.scene.domain.entity.TvShowEntity
import com.scene.domain.entity.TvShowsResponseEntity
import com.scene.remote.response.TvShowsResponse
import javax.inject.Inject

class TvShowsResponseRemoteToEntityMapper @Inject constructor() :
    Mapper<TvShowsResponse, TvShowsResponseEntity> {

    override fun map(input: TvShowsResponse): TvShowsResponseEntity {
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
