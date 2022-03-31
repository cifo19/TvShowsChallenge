package com.scene.homedomain.entity

data class TvShowsResponseEntity(
    val page: Int,
    val tvShowEntities: List<TvShowEntity>,
    val totalPages: Int,
    val totalResults: Int
)
