package com.cotters.spacexapp.companyinfo

import com.cotters.spacexapp.companyinfo.data.response.CompanyInfoDto
import com.cotters.spacexapp.companyinfo.domain.usecase.GetCompanyInfoUseCase
import com.cotters.spacexapp.service.SpaceXService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class CompanyInfoUseCaseShould {

    @RelaxedMockK
    private lateinit var serviceMock: SpaceXService

    private lateinit var getCompanyInfo: GetCompanyInfoUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getCompanyInfo = GetCompanyInfoUseCase(serviceMock)
    }

    @Test
    fun `fetch company info from service when asked`() {
        mockSuccessfulCompanyInfoResponse()
        runBlocking { getCompanyInfo.invoke() }
        coVerify { serviceMock.fetchCompanyInfo() }
    }

    @Test
    fun `return company info domain model after successful response`() {
        mockSuccessfulCompanyInfoResponse()

        val companyInfo = runBlocking { getCompanyInfo.invoke() }

        with(companyInfo!!) {
            assertThat(name, `is`(companyInfoMockData.name))
            assertThat(founder, `is`(companyInfoMockData.founder))
            assertThat(foundedYear, `is`(companyInfoMockData.founded))
            assertThat(employees, `is`(companyInfoMockData.employees))
            assertThat(launchSites, `is`(companyInfoMockData.launchSites))
            assertThat(valuation, `is`(companyInfoMockData.valuation))
            assertThat(summary, `is`(companyInfoMockData.summary))
        }
    }

    private fun mockSuccessfulCompanyInfoResponse() {
        coEvery { serviceMock.fetchCompanyInfo() } returns Response.success(companyInfoMockData)
    }

    @Test
    fun `return null when response is null`() {
        mockkNullSuccessfulResponse()
        val companyInfo = runBlocking { getCompanyInfo.invoke() }
        assertThat(companyInfo, `is`(nullValue()))
    }

    private fun mockkNullSuccessfulResponse() {
        coEvery { serviceMock.fetchCompanyInfo() } returns Response.success(null)
    }

    @Test
    fun `return null when response has error`() {
        mockkFailureResponse()
        val companyInfo = runBlocking { getCompanyInfo.invoke() }
        assertThat(companyInfo, `is`(nullValue()))
    }

    private fun mockkFailureResponse() {
        coEvery { serviceMock.fetchCompanyInfo() } returns Response.error(
            404,
            ResponseBody.create(null, testErrorMessage)
        )
    }

    companion object {
        val companyInfoMockData = CompanyInfoDto(
            name = "SpaceX",
            founder = "Elon Musk",
            founded = 2002,
            employees = 10000,
            launchSites = 3,
            valuation = 52000000000L,
            summary = "SpaceX designs, manufactures and launches advanced rockets and spacecraft. The company was founded in 2002 to revolutionize space technology, with the ultimate goal of enabling people to live on other planets.",
        )
        private const val testErrorMessage = "Test: Testing failure case."
    }
}
