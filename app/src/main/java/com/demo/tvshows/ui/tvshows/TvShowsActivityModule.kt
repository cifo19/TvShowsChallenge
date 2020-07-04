package com.demo.tvshows.ui.tvshows

import com.demo.tvshows.di.scope.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@SuppressWarnings("UnnecessaryAbstractClass")
@Module
interface TvShowsActivityModule {

    @PerFragment
    @ContributesAndroidInjector
    fun contributeTvShowsFragment(): TvShowsFragment
}
