package com.scene.homedata

import com.scene.homedata.local.datastore.LocalTvShowsDataStoreImpl
import com.scene.homedata.remote.datastore.RemoteTvShowsDataStoreImpl
import com.scene.homedomain.datastore.LocalTvShowsDataStore
import com.scene.homedomain.datastore.RemoteTvShowsDataStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
interface DataStoreModule {
    @Binds
    fun bindsCacheTvShowsDataStore(cacheTvShowsDataStoreImpl: LocalTvShowsDataStoreImpl): LocalTvShowsDataStore

    @Binds
    fun bindsRemoteTvShowsDataStore(remoteTvShowsDataStoreImpl: RemoteTvShowsDataStoreImpl): RemoteTvShowsDataStore
}
