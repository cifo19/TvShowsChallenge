package com.demo.tvshows.di

import com.demo.tvshows.datastore.CacheTvShowsDataStore
import com.demo.tvshows.datastore.CacheTvShowsDataStoreImpl
import com.demo.tvshows.datastore.RemoteTvShowsDataStore
import com.demo.tvshows.datastore.RemoteTvShowsDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
interface DataStoreModule {
    @Binds
    fun bindsCacheTvShowsDataStore(cacheTvShowsDataStoreImpl: CacheTvShowsDataStoreImpl): CacheTvShowsDataStore

    @Binds
    fun bindsRemoteTvShowsDataStore(remoteTvShowsDataStoreImpl: RemoteTvShowsDataStoreImpl): RemoteTvShowsDataStore
}
