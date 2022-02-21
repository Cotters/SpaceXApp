package com.cotters.spacexapp.launches.data.request

import com.squareup.moshi.Json

data class LaunchesRequestQueryDate(
    @field:Json(name = "\$gte") val start: String = createWithYear(earliestYear).start,
    @field:Json(name = "\$lte") val end: String = createWithYear(latestYear).end,
) {
    companion object {
        val allDates = LaunchesRequestQueryDate()

        private const val earliestYear = 2002
        private const val latestYear = 3002
        const val startDateTemplate = "%d-01-01T00:00:00.000Z"
        const val endDateTemplate = "%d-12-31T23:59:59.000Z"

        fun createWithYear(year: Int?): LaunchesRequestQueryDate {
            return LaunchesRequestQueryDate(
                startDateTemplate.format(year ?: earliestYear),
                endDateTemplate.format(year ?: latestYear)
            )
        }
    }
}
