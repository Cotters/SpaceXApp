package com.cotters.spacexapp

import com.cotters.spacexapp.data.SpaceXModule
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test

class SpaceXServiceShould {

    private val service = SpaceXModule.provideSpaceXService()

    @Test
    fun `return company info when company info is requested`() {
        val companyInfoResponse = runBlocking { service.fetchCompanyInfo() }
        with(companyInfoResponse.body()!!) {
            assertThat(name!!, `is`("SpaceX"))
            assertThat(founder!!, `is`("Elon Musk"))
            assertThat(founded!!, `is`(2002))
            assertTrue(employees!! > 0)
            assertTrue(launchSites!! > 0)
            assertTrue(valuation!! > 0L)
            assertTrue(summary!!.isNotBlank())
        }
    }
}
