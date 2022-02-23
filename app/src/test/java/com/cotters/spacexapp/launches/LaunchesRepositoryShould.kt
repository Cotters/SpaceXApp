package com.cotters.spacexapp.launches

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.RemoteMediator
import com.cotters.spacexapp.SampleData
import com.cotters.spacexapp.data.PaginationResponse
import com.cotters.spacexapp.data.SpaceXService
import com.cotters.spacexapp.launches.data.LaunchesRepository
import com.cotters.spacexapp.launches.data.LaunchesStore
import com.cotters.spacexapp.launches.data.database.Launch
import com.cotters.spacexapp.launches.data.database.LaunchesCacheTimer
import com.cotters.spacexapp.launches.data.request.LaunchesRequestBody
import com.cotters.spacexapp.launches.domain.LaunchesObjectMapper
import com.cotters.spacexapp.ui.values.Dimensions
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class LaunchesRepositoryShould {

    @RelaxedMockK
    private lateinit var service: SpaceXService

    @RelaxedMockK
    private lateinit var launchesStore: LaunchesStore

    @RelaxedMockK
    private lateinit var cacheTimer: LaunchesCacheTimer

    private lateinit var launchesRepository: LaunchesRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        launchesRepository = LaunchesRepository(
            service = service,
            launchesStore = launchesStore,
            cacheTimer = cacheTimer
        )
    }

    @Test
    fun `use service to update launches`() = runTest {
        mockSuccessfulLaunchesResponse()
        launchesRepository.load(LoadType.APPEND, mockk(relaxed = true))

        coVerify(exactly = 1) { service.fetchLaunches(any()) }
    }

    @Test
    fun `passes request options to api request when updating launches`() = runTest {
        mockSuccessfulLaunchesResponse()
        launchesRepository.load(LoadType.APPEND, mockk(relaxed = true))

        val slot = slot<LaunchesRequestBody>()
        coVerify(exactly = 1) { service.fetchLaunches(capture(slot)) }
        val requestOptions = slot.captured.options
        assertThat(requestOptions.limit, `is`(Dimensions.paginationSize))
        assertThat(requestOptions.page, `is`(1))
        assertThat(requestOptions.populate, `is`(listOf("rocket")))
    }

    @Test
    fun `store launches when fetched`() = runTest {
        mockSuccessfulLaunchesResponse()
        launchesRepository.load(LoadType.APPEND, mockk(relaxed = true))

        val slot = slot<List<Launch>>()
        coVerify(exactly = 1) { launchesStore.storeAll(capture(slot)) }

        val storedLaunch = slot.captured.first()
        val expected = LaunchesObjectMapper().dataToStore(allLaunches).first()
        assertThat(storedLaunch, `is`(expected))
    }

    @Test
    fun `skip refresh when cache is valid`() = runTest {
        every { cacheTimer.shouldRequestLaunches() } returns false

        val action = launchesRepository.initialize()

        assertThat(action, `is`(RemoteMediator.InitializeAction.SKIP_INITIAL_REFRESH))
    }

    @Test
    fun `refresh data when cache is stale`() = runTest {
       every { cacheTimer.shouldRequestLaunches() } returns true

        val action = launchesRepository.initialize()

        assertThat(action, `is`(RemoteMediator.InitializeAction.LAUNCH_INITIAL_REFRESH))
    }

    private fun mockSuccessfulLaunchesResponse() {
        coEvery { service.fetchLaunches(any()) } returns Response.success(
            PaginationResponse(
                items = allLaunches,
                totalItems = allLaunches.size,
                limit = 0,
                totalPages = 1,
                page = 1,
                pagingCounter = 1,
                hasPrevPage = false,
                hasNextPage = false,
                prevPage = null,
                nextPage = null,
            )
        )
    }

    companion object {
        val allLaunches = SampleData.launchDataResponse
    }
}
