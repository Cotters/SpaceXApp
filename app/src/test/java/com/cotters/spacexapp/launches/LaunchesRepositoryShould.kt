package com.cotters.spacexapp.launches

import com.cotters.spacexapp.SampleData
import com.cotters.spacexapp.launches.data.LaunchesRepository
import com.cotters.spacexapp.launches.data.room.Launch
import com.cotters.spacexapp.launches.domain.LaunchesStore
import com.cotters.spacexapp.service.SpaceXService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class LaunchesRepositoryShould {

    @RelaxedMockK
    private lateinit var service: SpaceXService

    @RelaxedMockK
    private lateinit var launchesStore: LaunchesStore

    private lateinit var launchesRepository: LaunchesRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        launchesRepository = LaunchesRepository(
            service = service,
            launchesStore = launchesStore
        )
    }

    @Test
    fun `use service to update launches`() = runTest {
        launchesRepository.updateLaunches()
        coVerify(exactly = 1) { service.fetchAllLaunches() }
    }

    @Test
    fun `store launches when fetched`() = runTest {
        mockSuccessfulLaunchesResponse()

        launchesRepository.updateLaunches()
        val slot = slot<List<Launch>>()
        coVerify(exactly = 1) { launchesStore.storeAll(capture(slot)) }

        val launchesStored = slot.captured
//        val expected = LaunchesObjectMapper().allDataToStore(allLaunches)

        val storeLaunch = launchesStored.first()
        val expectedLaunch = expected.first()
        assertThat(storeLaunch.missionName, `is`(expectedLaunch.name))
        assertThat(storeLaunch.dateUnix, `is`(expectedLaunch.dateUnix))
        assertThat(storeLaunch.rocket, `is`(expectedLaunch.rocket))
        assertThat(storeLaunch.success, `is`(expectedLaunch.success))
        assertThat(storeLaunch.links, `is`(expectedLaunch.links))
    }

    @Test
    fun `get launches from store`() {
        launchesRepository.getAllLaunches()
        coVerify(exactly = 1) { launchesStore.getAll() }
    }

    private fun mockSuccessfulLaunchesResponse() {
        coEvery { service.fetchAllLaunches() } returns Response.success(allLaunches)
    }

    companion object {
        val allLaunches = SampleData.allLaunchDataResponse
    }
}
