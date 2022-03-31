package com.scene.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.scene.cache.converters.Converters
import com.scene.cache.dao.TvShowsResponseDao
import com.scene.cache.entity.TvShowsResponseData

@Database(version = 1, entities = [TvShowsResponseData::class])
@TypeConverters(Converters::class)
abstract class TvShowsDataBase : RoomDatabase() {

    abstract fun tvShowResponseDao(): TvShowsResponseDao
}
