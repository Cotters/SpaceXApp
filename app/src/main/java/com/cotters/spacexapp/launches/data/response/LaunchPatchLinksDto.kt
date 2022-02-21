package com.cotters.spacexapp.launches.data.response

import com.squareup.moshi.Json

data class LaunchPatchLinksDto(
    @field:Json(name = "small") val small: String?,
    @field:Json(name = "large") val large: String?,
)
