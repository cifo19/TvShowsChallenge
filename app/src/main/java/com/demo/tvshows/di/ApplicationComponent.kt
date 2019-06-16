package com.demo.tvshows.di

import android.app.Application
import com.demo.tvshows.application.TvShowsApplication
import com.demo.tvshows.di.viewmodel.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class, NetworkModule::class, UtilityModule::class, AndroidInjectionModule::class,
        ActivitiesModule::class, ViewModelModule::class]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent

        @BindsInstance
        fun application(application: Application): Builder
    }

    fun inject(tvShowsApplication: TvShowsApplication)
}
