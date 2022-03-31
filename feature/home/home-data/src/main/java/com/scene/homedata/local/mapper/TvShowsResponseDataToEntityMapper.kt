package com.scene.homedata.local.mapper

import com.scene.homedata.local.entity.TvShowsResponseData
import com.scene.homedomain.entity.TvShowEntity
import com.scene.homedomain.entity.TvShowsResponseEntity
import javax.inject.Inject

class TvShowsResponseDataToEntityMapper @Inject constructor() {

    fun map(tvShowsResponseData: TvShowsResponseData): TvShowsResponseEntity {
        return with(tvShowsResponseData) {
            val tvShowEntities = tvShowData.map {
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
