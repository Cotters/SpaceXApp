//package com.cotters.spacexapp.launches
//
//import androidx.paging.PagingConfig
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.cotters.spacexapp.SampleData
//import com.cotters.spacexapp.launches.data.LaunchesRepository
//import com.cotters.spacexapp.launches.domain.LaunchesObjectMapper
//import com.cotters.spacexapp.launches.domain.usecase.GetLaunchesUseCase
//import io.mockk.MockKAnnotations
//import io.mockk.coVerify
//import io.mockk.impl.annotations.RelaxedMockK
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runTest
//import org.hamcrest.CoreMatchers.`is`
//import org.hamcrest.CoreMatchers.instanceOf
//import org.hamcrest.MatcherAssert.assertThat
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import org.junit.Ignore
//import org.junit.Test
//
//@ExperimentalCoroutinesApi
//class LaunchesUseCaseShould {
//
//    @RelaxedMockK
//    private lateinit var launchesRepository: LaunchesRepository
//
//    private lateinit var getLaunchesUseCase: GetLaunchesUseCase
//
//    @Before
//    fun setUp() {
//        MockKAnnotations.init(this)
//        getLaunchesUseCase = GetLaunchesUseCase(launchesRepository)
//    }
//
////    @Test
////    fun `fetch from api when network connection available`() = runTest {
////        getLaunchesUseCase.invoke()
////        coVerify { launchesRepository.fetchLaunches(10, 0, listOf("rocket")) }
////    }
//
//    @Test
//    fun `return page when load is successful`() = runTest {
////        mockSuccessfulLaunchesResponse()
//
//        val pagingSource = GetLaunchesUseCase(launchesRepository)
//
////        assertEquals(
////            PagingSource.LoadResult.Page(
////                data = LaunchesObjectMapper().dataToDomain(launchData),
////                prevKey = null,
////                nextKey = 2
////            ),
////            pagingSource.load(
////                PagingSource.LoadParams.Refresh(
////                    key = null,
////                    loadSize = 2,
////                    placeholdersEnabled = false
////                )
////            )
////        )
//    }
//
//    @Ignore
//    @Test
//    fun `return error result when exception occurs`() = runTest {
////        mockExceptionInLaunchesResponse()
//
//        val pagingSource = GetLaunchesUseCase(launchesRepository)
//
//        assertThat(
//            pagingSource.load(
//                PagingSource.LoadParams.Refresh(
//                    key = null,
//                    loadSize = 2,
//                    placeholdersEnabled = false
//                )
//            ),
//            instanceOf(PagingSource.LoadResult.Error::class.java)
//        )
//    }
//
//    @Ignore
//    @Test
//    fun `return initial page as refresh key`() {
//        val pagingSource = GetLaunchesUseCase(launchesRepository)
//        assertThat(
//            pagingSource.getRefreshKey(PagingState(emptyList(), 1, PagingConfig(0), 0)),
//            `is`(GetLaunchesUseCase.initialPage)
//        )
//    }
//
////    private fun mockSuccessfulLaunchesResponse() {
////        coEvery { launchesRepository.updateLaunches() } returns Response.success(
////            PaginationResponse(
////                items = launchData,
////                totalDocs = 0,
////                offset = 0,
////                limit = 10,
////                totalPages = 2,
////                page = 1,
////                pagingCounter = 2,
////                hasPrevPage = false,
////                hasNextPage = true,
////                prevPage = null,
////                nextPage = 2,
////            )
////        )
////    }
////
////    private fun mockExceptionInLaunchesResponse() {
////        coEvery { launchesRepository.fetchLaunches(any()) } throws IOException(testErrorMessage)
////    }
//
//    companion object {
//        private val launchData = SampleData.launchDataResponse
//        private const val testErrorMessage = "Test: Testing failure case."
//    }
//}
