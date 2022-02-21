package com.cotters.spacexapp.launches.data.response

import com.squareup.moshi.Json

class LaunchDto(
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "date_unix") val dateUnix: Long?,
    @field:Json(name = "rocket") val rocket: RocketDto?,
    @field:Json(name = "success") val success: Boolean?,
    @field:Json(name = "links") val links: LaunchLinksDto?,
)
