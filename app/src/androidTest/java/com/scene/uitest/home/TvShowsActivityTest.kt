package com.scene.uitest

import androidx.test.ext.junit.rules.activityScenarioRule
import com.scene.homepresentation.TvShowsActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class TvShowsActivityTest {

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var tvShowsActivityScenarioRule = activityScenarioRule<TvShowsActivity>()

    @Test
    fun testActivity() {
        assertThat(true).isTrue
    }
}
