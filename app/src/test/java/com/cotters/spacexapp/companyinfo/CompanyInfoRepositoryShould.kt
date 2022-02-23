package com.cotters.spacexapp.companyinfo

import com.cotters.spacexapp.SampleData
import com.cotters.spacexapp.companyinfo.data.CompanyInfoRepository
import com.cotters.spacexapp.companyinfo.data.CompanyInfoStore
import com.cotters.spacexapp.companyinfo.data.room.CompanyInfo
import com.cotters.spacexapp.data.CompanyInfoCacheTimer
import com.cotters.spacexapp.data.SpaceXService
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class CompanyInfoRepositoryShould {

    @RelaxedMockK
    private lateinit var cacheTimer: CompanyInfoCacheTimer

    @RelaxedMockK
    private lateinit var service: SpaceXService

    @RelaxedMockK
    private lateinit var store: CompanyInfoStore

    private lateinit var repository: CompanyInfoRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CompanyInfoRepository(service, store, CompanyInfoMapper(), cacheTimer)
        invalidateCache()
    }

    @Test
    fun `fetch company info from api`() = runTest {
        repository.getCompanyInfo()

        coVerify { service.fetchCompanyInfo() }
    }

    @Test
    fun `store company info when response is successful`() = runTest {
        mockSuccessfulResponse()

        repository.getCompanyInfo()

        val slot = slot<CompanyInfo>()
        coVerify { store.invoke(capture(slot)) }
        val storedCompanyInfo = slot.captured

        assertThat(storedCompanyInfo.name, `is`(companyInfoDto.name))
        assertThat(storedCompanyInfo.founder, `is`(companyInfoDto.founder))
        assertThat(storedCompanyInfo.foundedYear, `is`(companyInfoDto.founded))
        assertThat(storedCompanyInfo.employees, `is`(companyInfoDto.employees))
        assertThat(storedCompanyInfo.launchSites, `is`(companyInfoDto.launchSites))
        assertThat(storedCompanyInfo.valuation, `is`(companyInfoDto.valuation))
        assertThat(storedCompanyInfo.summary, `is`(companyInfoDto.summary))
    }

    @Test
    fun `check if it should request new company info from the cache timer`() = runTest {
        repository.getCompanyInfo()
        coVerify { cacheTimer.shouldRequestCompanyInfo() }
    }

    @Test
    fun `return stored company info when response is empty`() = runTest {
        mockUnsuccessfulResponse()
        mockStoredCompanyInfo()

        val companyInfoResponse = repository.getCompanyInfo()

        assertThat(companyInfoResponse, `is`(companyInfo))
    }

    @Test
    fun `fetch company info when stored company info is stale`() = runTest {
        repository.getCompanyInfo()

        invalidateCache()

        repository.getCompanyInfo()

        coVerify(exactly = 2) { service.fetchCompanyInfo() }
    }

    @Test
    fun `not fetch company info when stored company info is not stale`() = runTest {
        repository.getCompanyInfo()

        every { cacheTimer.shouldRequestCompanyInfo() } returns false

        repository.getCompanyInfo()

        coVerify(exactly = 1) { service.fetchCompanyInfo() }
    }

    @Test
    fun `update cache timeout when company info response is successful`() = runTest {
        mockSuccessfulResponse()

        repository.getCompanyInfo()

        coVerify(exactly = 1) { cacheTimer.reset() }
    }

    @Test
    fun `invalidate cache timer when company info request failed`() = runTest {
        mockUnsuccessfulResponse()

        repository.getCompanyInfo()

        coVerify(exactly = 1) { cacheTimer.invalidate() }
    }

    private fun invalidateCache() {
        every { cacheTimer.shouldRequestCompanyInfo() } returns true
    }

    private fun mockSuccessfulResponse() {
        coEvery { service.fetchCompanyInfo() } returns Response.success(companyInfoDto)
    }

    private fun mockUnsuccessfulResponse() {
        coEvery { service.fetchCompanyInfo() } returns Response.error(
            401,
            ResponseBody.create(null, testErrorMessage)
        )
    }

    private fun mockStoredCompanyInfo() {
        coEvery { store.get() } returns companyInfo
    }

    companion object {
        val companyInfoDto = SampleData.companyInfoDto
        val companyInfo = SampleData.companyInfo
        const val testErrorMessage = "Testing errors"
    }


}
