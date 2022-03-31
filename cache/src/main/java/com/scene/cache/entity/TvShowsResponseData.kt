package com.scene.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_show_response")
data class TvShowsResponseData(
    @PrimaryKey @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "results") val tvShowData: List<TvShowData>,
    @ColumnInfo(name = "totalPages") val totalPages: Int,
    @ColumnInfo(name = "totalResults") val totalResults: Int,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: Long
)
