package com.cotters.spacexapp.companyinfo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cotters.spacexapp.companyinfo.domain.model.CompanyInfo
import com.cotters.spacexapp.companyinfo.domain.usecase.GetCompanyInfoUseCase
import com.cotters.spacexapp.companyinfo.presentation.CompanyInfoViewModel
import com.cotters.spacexapp.extensions.toDollarString
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CompanyInfoViewModelShould {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var getCompanyInfoUseCase: GetCompanyInfoUseCase

    private lateinit var viewModel: CompanyInfoViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        MockKAnnotations.init(this)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = CompanyInfoViewModel(mockk(relaxed = true), getCompanyInfoUseCase)
    }

    @Test
    fun `fetch company info when view model created`() {
        coVerify { getCompanyInfoUseCase.invoke() }
    }

    @Test
    fun `update company info state when response is successful`() {
        mockSuccessfulResponse()

        initViewModel()

        assertThat(viewModel.companyInfo.value, `is`(companyInfoTemplate.format(
            companyInfoMockData.name,
            companyInfoMockData.founder,
            companyInfoMockData.foundedYear,
            companyInfoMockData.employees,
            companyInfoMockData.launchSites,
            companyInfoMockData.valuation.toDollarString()
        )))
    }

    @Test
    fun `show error when company info is null`() {
        mockFailedResponse()

        initViewModel()

        assertThat(viewModel.companyInfo.value, `is`(failedErrorMessage))
    }

    private fun mockSuccessfulResponse() {
        coEvery { getCompanyInfoUseCase.invoke() } returns companyInfoMockData
    }

    private fun mockFailedResponse() {
        coEvery { getCompanyInfoUseCase.invoke() } returns null
    }

    companion object {
        const val companyInfoTemplate = "%s was founded by %s in %d. It now has %d employees, %d launch sites, and is valued at USD %s."
        const val failedErrorMessage = "There was a problem retrieving company information."
        val companyInfoMockData = CompanyInfo(
            name = "SpaceX",
            founder = "Elon Musk",
            foundedYear = 2002,
            employees = 10000,
            launchSites = 3,
            valuation = 52000000000L,
            summary = "SpaceX designs, manufactures and launches advanced rockets and spacecraft. The company was founded in 2002 to revolutionize space technology, with the ultimate goal of enabling people to live on other planets.",
        )
    }
}
