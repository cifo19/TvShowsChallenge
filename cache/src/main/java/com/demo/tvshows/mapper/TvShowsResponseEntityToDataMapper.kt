package com.demo.tvshows.mapper

import com.demo.tvshows.Mapper
import com.demo.tvshows.entity.TvShowsResponseEntity
import com.demo.tvshows.entity.TvShowData
import com.demo.tvshows.entity.TvShowsResponseData
import javax.inject.Inject

class TvShowsResponseEntityToDataMapper @Inject constructor() : Mapper<TvShowsResponseEntity, TvShowsResponseData> {

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
