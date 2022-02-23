package com.cotters.spacexapp.data

import com.cotters.spacexapp.companyinfo.data.response.CompanyInfoDto
import com.cotters.spacexapp.launches.data.request.LaunchesRequestBody
import com.cotters.spacexapp.launches.data.response.LaunchDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SpaceXService {
    @GET("/v4/company")
    suspend fun fetchCompanyInfo(): Response<CompanyInfoDto>

    @POST("/v5/launches/query")
    suspend fun fetchLaunches(@Body launchesRequestBody: LaunchesRequestBody): Response<PaginationResponse<LaunchDto>>
}
