package com.demo.tvshows.application

import android.app.Activity
import android.app.Application
import com.demo.tvshows.di.ApplicationComponent
import com.demo.tvshows.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class TvShowsApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    private lateinit var applicationComponent: ApplicationComponent

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder().application(this).build()
        applicationComponent.inject(this)
    }
}
