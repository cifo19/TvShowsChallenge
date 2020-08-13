package com.demo.tvshows.mapper

import com.demo.tvshows.Mapper
import com.demo.tvshows.entity.TvShowsResponseEntity
import com.demo.tvshows.response.TvShowsResponse
import com.demo.tvshows.response.model.TvShow
import javax.inject.Inject

class TvShowsResponseEntityToRemoteMapper @Inject constructor() : Mapper<TvShowsResponseEntity, TvShowsResponse> {

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
