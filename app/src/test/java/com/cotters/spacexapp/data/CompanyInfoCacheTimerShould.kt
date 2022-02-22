package com.cotters.spacexapp.data

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CompanyInfoCacheTimerShould {

    @RelaxedMockK
    private lateinit var systemClock: SystemClock

    private lateinit var cacheTimer: CompanyInfoCacheTimer

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        cacheTimer = CompanyInfoCacheTimer(systemClock, timeoutPeriod)
        mockSystemCurrentTimeTo(nowMocked)
    }

    @Test
    fun `set last updated to 0 when initialised`() {
        assertThat(cacheTimer.lastUpdated, `is`(0))
    }

    @Test
    fun `set last updated to now when resetting`() {
        cacheTimer.reset()

        assertThat(cacheTimer.lastUpdated, `is`(nowMocked))
    }

    @Test
    fun `set company last updated to 0 when invalidating`() {
        cacheTimer.invalidate()

        assertThat(cacheTimer.lastUpdated, `is`(0))
    }

    @Test
    fun `not advise fetching company info when timeout has not passed`() {
        cacheTimer.reset()

        assertFalse(cacheTimer.shouldRequestCompanyInfo())
    }

    @Test
    fun `advise fetching company info when timeout has passed`() {
        cacheTimer.reset()

        val futureTime = nowMocked + (timeoutPeriod * 2)
        mockSystemCurrentTimeTo(futureTime)

        assertTrue(cacheTimer.shouldRequestCompanyInfo())
    }

    @Test
    fun `advise fetching company info when cache invalidated`() {
        cacheTimer.invalidate()

        assertTrue(cacheTimer.shouldRequestCompanyInfo())
    }

    private fun mockSystemCurrentTimeTo(testTime: Long) {
        every { systemClock.nowInMilliseconds() } returns testTime
    }

    companion object {
        private const val timeoutPeriod = 10L
        private const val nowMocked = 1000L
    }
}
