package com.demo.tvshows.ui.tvshows

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.demo.tvshows.ui.base.BaseActivity
import dagger.Binds
import dagger.Module
import dagger.Provides

@SuppressWarnings("UnnecessaryAbstractClass")
@Module
abstract class TvShowsActivityModule {

    @Binds
    abstract fun bindActivity(tvShowsActivity: TvShowsActivity): BaseActivity

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideViewModel(activity: BaseActivity, viewModelFactory: ViewModelProvider.Factory): TvShowsViewModel {
            return ViewModelProviders.of(activity, viewModelFactory)[TvShowsViewModel::class.java]
        }
    }
}
