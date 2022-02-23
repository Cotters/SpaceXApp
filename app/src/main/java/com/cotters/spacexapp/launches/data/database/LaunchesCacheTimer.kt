package com.cotters.spacexapp.launches.data.database

import com.cotters.spacexapp.data.CacheTimer
import com.cotters.spacexapp.data.SystemClock

class LaunchesCacheTimer(
    systemClock: SystemClock = SystemClock(),
    timeoutPeriod: Long = hourInMilliseconds,
) : CacheTimer(systemClock, timeoutPeriod) {

    fun shouldRequestLaunches() = isValid
}
