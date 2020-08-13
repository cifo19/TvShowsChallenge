package com.demo.tvshows.mapper

import com.demo.tvshows.Mapper
import com.demo.tvshows.entity.TvShowEntity
import com.demo.tvshows.entity.TvShowsResponseEntity
import com.demo.tvshows.entity.TvShowsResponseData
import javax.inject.Inject

class TvShowsResponseDataToEntityMapper @Inject constructor() : Mapper<TvShowsResponseData, TvShowsResponseEntity> {

    override fun map(input: TvShowsResponseData): TvShowsResponseEntity {
        return with(input) {
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
