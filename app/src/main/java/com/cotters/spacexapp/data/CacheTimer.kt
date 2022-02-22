package com.cotters.spacexapp.data

import java.util.concurrent.TimeUnit

abstract class CacheTimer(
    private val systemClock: SystemClock = SystemClock(),
    private val timeoutPeriod: Long = hourInMilliseconds,
) {

    // TODO: Use SharedPreferences or DB to store this.
    var lastUpdated: Long = 0
        private set

    val isValid: Boolean
        get() = systemClock.nowInMilliseconds() - lastUpdated > timeoutPeriod

    fun reset() {
        lastUpdated = systemClock.nowInMilliseconds()
    }

    fun invalidate() {
        lastUpdated = 0
    }

    companion object {
        val hourInMilliseconds = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
    }
}
