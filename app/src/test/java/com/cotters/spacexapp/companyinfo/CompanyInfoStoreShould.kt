package com.cotters.spacexapp.companyinfo

import com.cotters.spacexapp.SampleData
import com.cotters.spacexapp.companyinfo.data.CompanyInfoStore
import com.cotters.spacexapp.companyinfo.data.database.CompanyInfoDao
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class CompanyInfoStoreShould {

    @RelaxedMockK
    private lateinit var dao: CompanyInfoDao

    private lateinit var store: CompanyInfoStore

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        store = CompanyInfoStore(dao)
    }

    @Test
    fun `insert into dao when storing`() {
        store(companyInfo)

        verify { dao.insert(companyInfo) }
    }

    @Test
    fun `return company info from dao when requested`() {
        every { dao.get() } returns companyInfo

        val returnedCompanyInfo = store.get()

        verify { dao.get() }

        assertThat(returnedCompanyInfo, `is`(companyInfo))
    }

    companion object {
        private val companyInfo = SampleData.companyInfo
    }
}
