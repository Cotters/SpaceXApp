package com.cotters.spacexapp.launches.data.request

class LaunchesRequestOptions(
    val limit: Int,
    var page: Int = 0,
    val populate: List<String>,
    val sort: LaunchRequestSortOptions = LaunchRequestSortOptions(sortDirection = Sort.SortDirection.ASCENDING),
)

