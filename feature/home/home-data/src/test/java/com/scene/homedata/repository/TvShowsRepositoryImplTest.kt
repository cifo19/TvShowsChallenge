package com.scene.homedata.repository

import com.scene.homedomain.datastore.TvShowsDataStore
import com.scene.homedomain.entity.TvShowEntity
import com.scene.homedomain.entity.TvShowsResponseEntity
import io.mockk.Called
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TvShowsRepositoryImplTest {

    @MockK
    private lateinit var remoteTvShowsDataStoreImpl: TvShowsDataStore.Remote

    @MockK
    private lateinit var localTvShowsDataStore: TvShowsDataStore.Local

    private lateinit var tvShowsRepositoryImpl: TvShowsRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        tvShowsRepositoryImpl = TvShowsRepositoryImpl(
            remoteTvShowsDataStoreImpl,
            localTvShowsDataStore
        )
    }

    @Test
    fun `Return cached entities when the cache exist`() = runBlockingTest {
        val expectedResponse = getDummyTvShowsResponseEntity()
        coEvery { localTvShowsDataStore.getPopularTvShowsResponseEntity(any()) } returns expectedResponse

        val actualResponse = tvShowsRepositoryImpl.fetchPopularTvShows(pageIndex = 1)

        coVerify { localTvShowsDataStore.getPopularTvShowsResponseEntity(any()) }
        coVerify { remoteTvShowsDataStoreImpl wasNot Called }
        assertThat(actualResponse).isEqualTo(expectedResponse)
    }

    @Test
    fun `Return remote entities and insert them to cache when the cache does not exist`() = runBlockingTest {
        val expectedResponse = getDummyTvShowsResponseEntity()
        coEvery { localTvShowsDataStore.getPopularTvShowsResponseEntity(any()) } returns null
        coEvery { localTvShowsDataStore.insertTvShowsResponseEntity(any()) } returns Unit
        coEvery { remoteTvShowsDataStoreImpl.getPopularTvShowsResponseEntity(any()) } returns expectedResponse

        val actualResponse = tvShowsRepositoryImpl.fetchPopularTvShows(pageIndex = 1)

        coVerify { remoteTvShowsDataStoreImpl.getPopularTvShowsResponseEntity(any()) }
        coVerify { localTvShowsDataStore.insertTvShowsResponseEntity(expectedResponse) }
        assertThat(actualResponse).isEqualTo(expectedResponse)
    }

    private fun getDummyTvShowsResponseEntity(): TvShowsResponseEntity {
        val tvShowEntities = listOf(
            TvShowEntity(
                backdropPath = "backdropPath",
                firstAirDate = "firstAirDate",
                genreIds = emptyList(),
                id = 1,
                name = "name",
                originCountry = emptyList(),
                originalLanguage = "originalLanguage",
                originalName = "originalName",
                overview = "overview",
                popularity = 0.0,
                posterPath = "posterPath",
                voteAverage = 0.0,
                voteCount = 0
            )
        )
        return TvShowsResponseEntity(page = 1, tvShowEntities = tvShowEntities, totalPages = 0, totalResults = 0)
    }
}
