package com.scene.ui_tests.home

import androidx.test.core.app.launchActivity
import com.scene.homepresentation.TvShowsActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class TvShowsFragmentTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun foo() {
        launchActivity<TvShowsActivity>()
    }
}
