package com.scene.homedata.di

import com.scene.homedata.local.datastore.LocalTvShowsDataStoreImpl
import com.scene.homedata.remote.datastore.RemoteTvShowsDataStoreImpl
import com.scene.homedomain.datastore.TvShowsDataStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataStoreModule {

    @Binds
    fun bindsLocalTvShowsDataStore(localTvShowsDataStoreImpl: LocalTvShowsDataStoreImpl): TvShowsDataStore.Local

    @Binds
    fun bindsRemoteTvShowsDataStore(remoteTvShowsDataStoreImpl: RemoteTvShowsDataStoreImpl): TvShowsDataStore.Remote
}
