package com.scene.app.repository

import com.scene.domain.datastore.CacheTvShowsDataStore
import com.scene.domain.datastore.RemoteTvShowsDataStore
import com.scene.domain.entity.TvShowEntity
import com.scene.domain.entity.TvShowsResponseEntity
import com.scene.data.repository.TvShowsRepositoryImpl
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
    private lateinit var remoteTvShowsDataStoreImpl: RemoteTvShowsDataStore

    @MockK
    private lateinit var cacheTvShowsDataStoreImpl: CacheTvShowsDataStore

    private lateinit var tvShowsRepositoryImpl: TvShowsRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        tvShowsRepositoryImpl = TvShowsRepositoryImpl(remoteTvShowsDataStoreImpl, cacheTvShowsDataStoreImpl)
    }

    @Test
    fun `Return cached entities when the cache exist`() = runBlockingTest {
        val expectedResponse = getDummyTvShowsResponseEntity()
        coEvery { cacheTvShowsDataStoreImpl.getPopularTvShowsResponseEntity(any()) } returns expectedResponse

        val actualResponse = tvShowsRepositoryImpl.fetchPopularTvShows(pageIndex = 1)

        coVerify { cacheTvShowsDataStoreImpl.getPopularTvShowsResponseEntity(any()) }
        coVerify { remoteTvShowsDataStoreImpl wasNot Called }
        assertThat(actualResponse).isEqualTo(expectedResponse)
    }

    @Test
    fun `Return remote entities and insert them to cache when the cache does not exist`() = runBlockingTest {
        val expectedResponse = getDummyTvShowsResponseEntity()
        coEvery { cacheTvShowsDataStoreImpl.getPopularTvShowsResponseEntity(any()) } returns null
        coEvery { cacheTvShowsDataStoreImpl.insertTvShowsResponseEntity(any()) } returns Unit
        coEvery { remoteTvShowsDataStoreImpl.getPopularTvShowsResponseEntity(any()) } returns expectedResponse

        val actualResponse = tvShowsRepositoryImpl.fetchPopularTvShows(pageIndex = 1)

        coVerify { remoteTvShowsDataStoreImpl.getPopularTvShowsResponseEntity(any()) }
        coVerify { cacheTvShowsDataStoreImpl.insertTvShowsResponseEntity(expectedResponse) }
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
