package com.demo.tvshows.di

import com.demo.tvshows.di.scope.PerActivity
import com.demo.tvshows.ui.tvshows.TvShowsActivity
import com.demo.tvshows.ui.tvshows.TvShowsActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@SuppressWarnings("UnnecessaryAbstractClass")
@Module
abstract class ActivitiesModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [TvShowsActivityModule::class])
    internal abstract fun contributeTvShowsActivity(): TvShowsActivity
}
