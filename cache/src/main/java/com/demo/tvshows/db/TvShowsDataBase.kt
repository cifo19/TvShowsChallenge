package com.demo.tvshows.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demo.tvshows.converters.Converters
import com.demo.tvshows.dao.TvShowsResponseDao
import com.demo.tvshows.entity.TvShowsResponseData

@Database(version = 1, entities = [TvShowsResponseData::class])
@TypeConverters(Converters::class)
abstract class TvShowsDataBase : RoomDatabase() {

    abstract fun tvShowResponseDao(): TvShowsResponseDao
}
