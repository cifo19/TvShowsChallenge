package com.demo.tvshows.cache.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.demo.tvshows.cache.dao.TvShowDao
import com.demo.tvshows.cache.db.TvShowsDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

private const val TV_SHOW_DATABASE_NAME = "tv_show_database"

@InstallIn(ApplicationComponent::class)
@Module
object CacheModule {

    @Provides
    @Singleton
    fun provideTvShowDatabase(@ApplicationContext context: Context): RoomDatabase {
        return Room.databaseBuilder(context, TvShowsDataBase::class.java, TV_SHOW_DATABASE_NAME).build()
    }

    @Singleton
    @Provides
    fun provideDao(tvShowsDataBase: TvShowsDataBase): TvShowDao {
        return tvShowsDataBase.tvShowDao()
    }
}
