package com.cotters.spacexapp.companyinfo.data.database

import com.cotters.spacexapp.data.CacheTimer
import com.cotters.spacexapp.data.SystemClock

class CompanyInfoCacheTimer(
    systemClock: SystemClock = SystemClock(),
    timeoutPeriod: Long = hourInMilliseconds,
) : CacheTimer(systemClock, timeoutPeriod) {

    fun shouldRequestCompanyInfo() = isValid
}
