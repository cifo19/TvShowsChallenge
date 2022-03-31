package com.scene.homedata.local.mapper

import com.scene.homedata.local.entity.TvShowData
import com.scene.homedata.local.entity.TvShowsResponseData
import com.scene.homedomain.entity.TvShowsResponseEntity
import javax.inject.Inject

class TvShowsResponseEntityToDataMapper @Inject constructor() {

    fun map(tvShowsResponseEntity: TvShowsResponseEntity): TvShowsResponseData {
        return with(tvShowsResponseEntity) {
            val tvShowData = this.tvShowEntities.map {
                TvShowData(
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
            val createdAt = System.currentTimeMillis()
            TvShowsResponseData(page, tvShowData, totalPages, totalResults, createdAt, createdAt)
        }
    }
}
