package com.cotters.spacexapp.launches.data.request

import com.squareup.moshi.Json

data class LaunchRequestSortOptions(
    @field:Json(name = "date_unix") val sortDirection: String,
) {
    constructor(sortDirection: Sort.SortDirection) : this(sortDirection.value)
}

data class Sort(val direction: SortDirection = SortDirection.ASCENDING) {
    enum class SortDirection(val value: String) {
        ASCENDING("asc"), DESCENDING("desc")
    }

    enum class MissionStatus {
        SUCCESS, FAILED, ALL
    }
}
