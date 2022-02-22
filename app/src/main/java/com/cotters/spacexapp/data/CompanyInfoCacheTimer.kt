package com.cotters.spacexapp.data

class CompanyInfoCacheTimer(
    systemClock: SystemClock = SystemClock(),
    timeoutPeriod: Long = hourInMilliseconds,
) : CacheTimer(systemClock, timeoutPeriod) {

    fun shouldRequestCompanyInfo(): Boolean {
        return isValid
    }
}
