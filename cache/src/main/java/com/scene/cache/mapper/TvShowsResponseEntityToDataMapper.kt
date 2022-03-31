package com.scene.cache.mapper

import com.scene.domain.Mapper
import com.scene.domain.entity.TvShowsResponseEntity
import com.scene.cache.entity.TvShowData
import com.scene.cache.entity.TvShowsResponseData
import javax.inject.Inject

class TvShowsResponseEntityToDataMapper @Inject constructor() :
    Mapper<TvShowsResponseEntity, TvShowsResponseData> {

    override fun map(input: TvShowsResponseEntity): TvShowsResponseData {
        return with(input) {
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
