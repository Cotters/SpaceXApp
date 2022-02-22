package com.cotters.spacexapp.companyinfo

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cotters.spacexapp.R
import com.cotters.spacexapp.SampleData
import com.cotters.spacexapp.companyinfo.domain.usecase.GetCompanyInfoUseCase
import com.cotters.spacexapp.companyinfo.presentation.CompanyInfoViewModel
import com.cotters.spacexapp.extensions.toDollarString
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
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
    private lateinit var context: Context

    @RelaxedMockK
    private lateinit var getCompanyInfoUseCase: GetCompanyInfoUseCase

    private lateinit var viewModel: CompanyInfoViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        MockKAnnotations.init(this)
        mockStringResources()
        initViewModel()
    }

    private fun mockStringResources() {
        every { context.getString(R.string.companyInfoTemplate) } returns companyInfoTemplate
        every { context.getString(R.string.failedErrorMessage) } returns failedErrorMessage
    }

    private fun initViewModel() {
        viewModel = CompanyInfoViewModel(context, getCompanyInfoUseCase)
    }

    @Test
    fun `fetch company info when view model initialised`() {
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
        val companyInfoMockData = SampleData.companyInfoDomain
    }
}
