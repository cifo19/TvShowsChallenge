package com.demo.tvshows.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.tvshows.ui.tvshows.TvShowsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@SuppressWarnings("UnnecessaryAbstractClass")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TvShowsViewModel::class)
    abstract fun bindTvShowsViewModel(tvShowsViewModel: TvShowsViewModel): ViewModel
}
