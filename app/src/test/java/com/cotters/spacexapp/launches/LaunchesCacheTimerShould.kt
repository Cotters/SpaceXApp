package com.cotters.spacexapp.launches

import com.cotters.spacexapp.data.SystemClock
import com.cotters.spacexapp.launches.data.database.LaunchesCacheTimer
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LaunchesCacheTimerShould {

    @RelaxedMockK
    private lateinit var systemClock: SystemClock

    private lateinit var cacheTimer: LaunchesCacheTimer

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        cacheTimer = LaunchesCacheTimer(systemClock, timeoutPeriod)
        mockSystemCurrentTimeTo(nowMocked)
    }

    @Test
    fun `set last updated to 0 when initialised`() {
        assertThat(cacheTimer.lastUpdated, CoreMatchers.`is`(0))
    }

    @Test
    fun `set last updated to now when resetting`() {
        cacheTimer.reset()

        assertThat(cacheTimer.lastUpdated, CoreMatchers.`is`(nowMocked))
    }

    @Test
    fun `set company last updated to 0 when invalidating`() {
        cacheTimer.invalidate()

        assertThat(cacheTimer.lastUpdated, CoreMatchers.`is`(0))
    }

    @Test
    fun `not advise fetching company info when timeout has not passed`() {
        cacheTimer.reset()

        assertFalse(cacheTimer.shouldRequestLaunches())
    }

    @Test
    fun `advise fetching company info when timeout has passed`() {
        cacheTimer.reset()

        val futureTime = nowMocked + (timeoutPeriod * 2)
        mockSystemCurrentTimeTo(futureTime)

        assertTrue(cacheTimer.shouldRequestLaunches())
    }

    @Test
    fun `advise fetching company info when cache invalidated`() {
        cacheTimer.invalidate()

        assertTrue(cacheTimer.shouldRequestLaunches())
    }

    private fun mockSystemCurrentTimeTo(testTime: Long) {
        every { systemClock.nowInMilliseconds() } returns testTime
    }

    companion object {
        private const val timeoutPeriod = 10L
        private const val nowMocked = 1000L
    }
}
