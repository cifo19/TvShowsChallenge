package com.demo.tvshows.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demo.tvshows.converters.Converters
import com.demo.tvshows.dao.TvShowDao
import com.demo.tvshows.entity.TvShow

@Database(version = 1, entities = [TvShow::class])
@TypeConverters(Converters::class)
abstract class TvShowsDataBase : RoomDatabase() {

    abstract fun tvShowDao(): TvShowDao
}
