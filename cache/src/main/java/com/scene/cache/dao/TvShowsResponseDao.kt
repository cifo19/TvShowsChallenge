package com.scene.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.scene.cache.entity.TvShowsResponseData

@Dao
interface TvShowsResponseDao {

    @Query("SELECT * from tv_show_response where page == :pageIndex")
    suspend fun getTvShowResponseEntities(pageIndex: Int): TvShowsResponseData

    @Query("SELECT created_at from tv_show_response where page == :pageIndex")
    suspend fun getTvShowResponseEntityCreationTime(pageIndex: Int): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShowsResponse(tvShowsResponseData: TvShowsResponseData)

    @Query("DELETE FROM tv_show_response where page == :pageIndex")
    suspend fun deleteTvShowResponseData(pageIndex: Int)

    @Query("DELETE FROM tv_show_response")
    suspend fun deleteAll()
}
