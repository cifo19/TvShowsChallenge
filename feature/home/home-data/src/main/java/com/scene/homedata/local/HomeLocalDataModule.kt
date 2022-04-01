package com.scene.homedata.local

import android.content.Context
import androidx.room.Room
import com.scene.homedata.local.dao.TvShowsResponseDao
import com.scene.homedata.local.db.TvShowsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val TV_SHOW_DATABASE_NAME = "tv_show_database"

@InstallIn(SingletonComponent::class)
@Module
object HomeLocalDataModule {

    @Provides
    @Singleton
    fun provideTvShowDatabase(@ApplicationContext context: Context): TvShowsDatabase {
        return Room.databaseBuilder(context, TvShowsDatabase::class.java, TV_SHOW_DATABASE_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideDao(tvShowsDatabase: TvShowsDatabase): TvShowsResponseDao {
        return tvShowsDatabase.tvShowResponseDao()
    }
}
