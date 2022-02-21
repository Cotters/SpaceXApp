package com.cotters.spacexapp.launches.data.response

import com.squareup.moshi.Json

data class LaunchLinksDto(
    @field:Json(name = "patch") val patch: LaunchPatchLinksDto?,
    @field:Json(name = "article") val article: String?,
    @field:Json(name = "wikipedia") val wikipedia: String?,
    @field:Json(name = "webcast") val webcast: String?,
)
