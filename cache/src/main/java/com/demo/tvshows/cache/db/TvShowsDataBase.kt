package com.demo.tvshows.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demo.tvshows.cache.converters.Converters
import com.demo.tvshows.cache.dao.TvShowDao
import com.demo.tvshows.cache.entity.TvShow

@Database(version = 1, entities = [TvShow::class])
@TypeConverters(Converters::class)
abstract class TvShowsDataBase : RoomDatabase() {

    abstract fun tvShowDao(): TvShowDao
}
