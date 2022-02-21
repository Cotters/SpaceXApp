package com.cotters.spacexapp.launches.data.request

import com.squareup.moshi.Json

data class LaunchesRequestQuery(
    @field:Json(name = "date_utc")
    val date: LaunchesRequestQueryDate = LaunchesRequestQueryDate.allDates,
)
