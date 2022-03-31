package com.scene.app.di

import com.scene.domain.datastore.CacheTvShowsDataStore
import com.scene.cache.datastore.CacheTvShowsDataStoreImpl
import com.scene.domain.datastore.RemoteTvShowsDataStore
import com.scene.remote.datastore.RemoteTvShowsDataStoreImpl
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
