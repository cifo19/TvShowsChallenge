package com.demo.tvshows.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.tvshows.cache.entity.TvShow

@Dao
interface TvShowDao {

    @Query("SELECT * from tv_show")
    suspend fun getTvShows(): List<TvShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tvShow: TvShow)

    @Query("DELETE FROM tv_show")
    suspend fun deleteAll()
}