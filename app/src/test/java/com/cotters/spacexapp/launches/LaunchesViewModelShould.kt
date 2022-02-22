//package com.cotters.spacexapp.launches
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import com.cotters.spacexapp.extensions.dateString
//import com.cotters.spacexapp.extensions.timeString
//import com.cotters.spacexapp.extensions.toLocalDate
//import com.cotters.spacexapp.launches.domain.model.LaunchDomainModel
//import com.cotters.spacexapp.launches.domain.model.LaunchLinksDomainModel
//import com.cotters.spacexapp.launches.domain.model.Rocket
//import com.cotters.spacexapp.launches.domain.usecase.GetLaunchesUseCase
//import com.cotters.spacexapp.launches.presentation.LaunchesViewModel
//import com.cotters.spacexapp.launches.presentation.LaunchesViewModel.Companion.dayInSeconds
//import com.cotters.spacexapp.launches.presentation.model.LaunchDayDifference
//import com.cotters.spacexapp.launches.presentation.model.LaunchStatus
//import io.mockk.MockKAnnotations
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.impl.annotations.RelaxedMockK
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.flow
//import kotlinx.coroutines.test.UnconfinedTestDispatcher
//import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.test.setMain
//import org.hamcrest.CoreMatchers.`is`
//import org.hamcrest.MatcherAssert.assertThat
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import java.time.LocalDateTime
//import java.time.ZoneOffset
//
//@ExperimentalCoroutinesApi
//class LaunchesViewModelShould {
//
//    @get:Rule
//    val rule = InstantTaskExecutorRule()
//
//    @RelaxedMockK
//    private lateinit var getLaunchesUseCase: GetLaunchesUseCase
//
//    private lateinit var viewModel: LaunchesViewModel
//
//    @Before
//    fun setUp() {
//        Dispatchers.setMain(UnconfinedTestDispatcher())
//        MockKAnnotations.init(this)
//    }
//
//    private fun initViewModel() {
//        viewModel = LaunchesViewModel(getLaunchesUseCase)
//    }
//
//    @Test
//    fun `fetch new launches when initialised`() {
//        initViewModel()
//        coVerify(exactly = 1) { getLaunchesUseCase.invoke() }
//    }
//
//    @Test
//    fun `update launches when response is successful`() = runTest {
//        mockSuccessfulLaunchesResponse()
//
//        viewModel = LaunchesViewModel(getLaunchesUseCase)
//
//        val launch = viewModel.launchesFlow.last().first()
//        assertThat(launch.name, `is`(launchDomain.name))
//        val launchDate = launchDateTimestamp.toLocalDate()
//        val launchDateString = "${launchDate.dateString()} at ${launchDate.timeString()}"
//        assertThat(launch.date, `is`(launchDateString))
//        val launchTimeString = "${rocket.name} / ${rocket.type}"
//        assertThat(launch.rocketDetails, `is`(launchTimeString))
//        assertThat(launch.status, `is`(LaunchStatus.Successful))
//        assertThat(launch.links, `is`(launchDomain.links))
//        val launchDayDifference = LaunchDayDifference(((LocalDateTime.now()
//            .toEpochSecond(ZoneOffset.UTC) - launchDateTimestamp) / dayInSeconds).toInt())
//        assertThat(launch.dayDifference, `is`(launchDayDifference))
//    }
//
//    private fun mockSuccessfulLaunchesResponse() {
//        coEvery { getLaunchesUseCase.getAllLaunches() } returns flow { emit(listOf(launchDomain)) }
////        coEvery { useCaseMock.load(any()) } returns PagingSource.LoadResult.Page(
////            data = listOf(launchDomain),
////            prevKey = null,
////            nextKey = 2
////        )
//    }
//
//
//    companion object {
//        private const val dayDiff = 5
//        private val launchDateTimestamp =
//            LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + (dayInSeconds * dayDiff)
//
//        private val rocket = Rocket("Falcon 9", "rocket")
//
//        private val launchDomain = LaunchDomainModel(
//            name = "CRS-20",
//            dateUnix = launchDateTimestamp,
//            rocket = rocket,
//            success = true,
//            links = LaunchLinksDomainModel(null,
//                "https://article",
//                "https://wiki",
//                "https://webcast")
//        )
//
//    }
//
//}
