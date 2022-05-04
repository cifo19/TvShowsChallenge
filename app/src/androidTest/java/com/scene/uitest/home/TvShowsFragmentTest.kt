package com.scene.uitest.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import com.scene.app.R
import com.scene.homedata.remote.HomeRemoteDataModule
import com.scene.homedata.remote.response.TvShowsResponse
import com.scene.homedata.remote.service.MovieDbService
import com.scene.homepresentation.TvShowsFragment
import com.scene.uitest.util.launchFragmentInHiltContainer
import com.scene.uitest.util.parseFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Singleton

@HiltAndroidTest
@UninstallModules(HomeRemoteDataModule::class)
class TvShowsFragmentTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var movieDbService: MovieDbService

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun showTvShowsFragment() {
        // Given
        val popularTvShowsSuccessResponse = parseFile<TvShowsResponse>("get_popular_tv_shows_success.json")
        coEvery { movieDbService.getPopularTvShows(any()) } returns popularTvShowsSuccessResponse

        // When
        launchFragmentInHiltContainer<TvShowsFragment>()

        // Then
        val nameOfFirstMovie = popularTvShowsSuccessResponse.tvShows.first().name
        onView(withId(R.id.tvShowsRecyclerView))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tvShowsRecyclerView))
            .check(matches(hasDescendant(withText(nameOfFirstMovie))))
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object HomeRemoteDataModule {

        @Provides
        @Singleton
        fun provideMovieDatabaseService(): MovieDbService = mockk()
    }
}
