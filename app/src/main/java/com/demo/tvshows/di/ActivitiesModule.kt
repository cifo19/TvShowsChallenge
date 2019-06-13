package com.demo.tvshows.di

import com.demo.tvshows.ui.tvshows.TvShowsActivity
import com.demo.tvshows.di.scope.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @PerActivity
    @ContributesAndroidInjector
    internal abstract fun contributeTvShowsActivity(): TvShowsActivity
}
