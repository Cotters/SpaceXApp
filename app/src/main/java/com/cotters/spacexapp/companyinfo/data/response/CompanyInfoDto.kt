package com.cotters.spacexapp.companyinfo.data.response

import com.squareup.moshi.Json

data class CompanyInfoDto(
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "founder") val founder: String?,
    @field:Json(name = "founded") val founded: Int?,
    @field:Json(name = "employees") val employees: Int?,
    @field:Json(name = "launch_sites") val launchSites: Int?,
    @field:Json(name = "valuation") val valuation: Long?,
    @field:Json(name = "summary") val summary: String?,
)
