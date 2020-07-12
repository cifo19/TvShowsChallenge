package com.demo.tvshows.ui.tvshows

import com.demo.tvshows.di.scope.PerFragment
import com.demo.tvshows.ui.tvshows.tvshowdetail.TvShowDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@SuppressWarnings("UnnecessaryAbstractClass")
@Module
interface TvShowsActivityModule {

    @PerFragment
    @ContributesAndroidInjector
    fun contributeTvShowsFragment(): TvShowsFragment

    @PerFragment
    @ContributesAndroidInjector
    fun contributeTvShowDetailFragment(): TvShowDetailFragment
}
