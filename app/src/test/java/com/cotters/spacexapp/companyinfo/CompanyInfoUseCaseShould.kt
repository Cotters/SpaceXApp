package com.cotters.spacexapp.companyinfo

import com.cotters.spacexapp.SampleData
import com.cotters.spacexapp.companyinfo.data.CompanyInfoRepository
import com.cotters.spacexapp.companyinfo.domain.usecase.GetCompanyInfoUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class CompanyInfoUseCaseShould {

    @RelaxedMockK
    private lateinit var repository: CompanyInfoRepository

    private lateinit var getCompanyInfo: GetCompanyInfoUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getCompanyInfo = GetCompanyInfoUseCase(repository, CompanyInfoMapper())
    }

    @Test
    fun `fetch company info from service when asked`() {
        mockSuccessfulCompanyInfoResponse()

        runBlocking { getCompanyInfo.invoke() }

        coVerify(exactly = 1) { repository.getCompanyInfo() }
    }

    @Test
    fun `return company info domain model after successful response`() {
        mockSuccessfulCompanyInfoResponse()

        val companyInfo = runBlocking { getCompanyInfo.invoke() }

        with(companyInfo!!) {
            assertThat(name, `is`(companyInfoMockData.name))
            assertThat(founder, `is`(companyInfoMockData.founder))
            assertThat(foundedYear, `is`(companyInfoMockData.foundedYear))
            assertThat(employees, `is`(companyInfoMockData.employees))
            assertThat(launchSites, `is`(companyInfoMockData.launchSites))
            assertThat(valuation, `is`(companyInfoMockData.valuation))
            assertThat(summary, `is`(companyInfoMockData.summary))
        }
    }

    private fun mockSuccessfulCompanyInfoResponse() {
        coEvery { repository.getCompanyInfo() } returns companyInfoMockData
    }

    private fun mockkFailureResponse() {
        coEvery { repository.getCompanyInfo() } returns null
    }

    companion object {
        val companyInfoMockData = SampleData.companyInfo
    }
}
