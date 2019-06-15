package com.demo.tvshows.application

import android.app.Activity
import android.app.Application
import com.demo.tvshows.di.ApplicationComponent
import com.demo.tvshows.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
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

        initRxJavaErrorHandler()
    }

    private fun initRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler {
            if (it is UndeliverableException) {
                return@setErrorHandler
            }

            Thread.currentThread().uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), it)
        }
    }
}
