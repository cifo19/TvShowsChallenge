package com.scene.homedata.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.scene.homedata.local.converters.Converters
import com.scene.homedata.local.dao.TvShowsResponseDao
import com.scene.homedata.local.entity.TvShowsResponseData

@Database(version = 1, entities = [TvShowsResponseData::class])
@TypeConverters(Converters::class)
abstract class TvShowsDatabase : RoomDatabase() {

    abstract fun tvShowResponseDao(): TvShowsResponseDao
}
