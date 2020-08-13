package com.demo.tvshows.mapper

import com.demo.tvshows.Mapper
import com.demo.tvshows.entity.TvShowEntity
import com.demo.tvshows.entity.TvShowsResponseEntity
import com.demo.tvshows.response.TvShowsResponse
import javax.inject.Inject

class TvShowsResponseRemoteToEntityMapper @Inject constructor() : Mapper<TvShowsResponse, TvShowsResponseEntity> {

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
