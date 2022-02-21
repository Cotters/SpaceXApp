package com.cotters.spacexapp.service

import com.cotters.spacexapp.companyinfo.data.response.CompanyInfoDto
import com.cotters.spacexapp.launches.data.request.LaunchesRequestBody
import com.cotters.spacexapp.launches.data.response.LaunchDto
import com.cotters.spacexapp.launches.data.response.PaginationResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

@Module
@InstallIn(ViewModelComponent::class)
object SpaceXModule {

    private const val baseURL = "https://api.spacexdata.com"

    @Provides
    fun provideSpaceXService(): SpaceXService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SpaceXService::class.java)
    }
}

interface SpaceXService {
    @GET("/v4/company")
    suspend fun fetchCompanyInfo(): Response<CompanyInfoDto>

    @POST("/v5/launches/query")
    suspend fun fetchLaunches(@Body launchesRequestBody: LaunchesRequestBody): Response<PaginationResponse<LaunchDto>>
}
