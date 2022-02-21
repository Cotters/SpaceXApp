package com.cotters.spacexapp.companyinfo

import com.cotters.spacexapp.companyinfo.data.CompanyInfoRepository
import com.cotters.spacexapp.service.SpaceXService
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CompanyInfoRepositoryShould {

    @RelaxedMockK
    private lateinit var service: SpaceXService

    private lateinit var repository: CompanyInfoRepository


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CompanyInfoRepository(service)
    }

    @Test
    fun `fetch company info from api`() = runTest {
        repository.updateCompanyInfo()

        coVerify { service.fetchCompanyInfo() }
    }
}
