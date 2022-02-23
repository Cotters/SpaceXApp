package com.cotters.spacexapp.companyinfo.data

import com.cotters.spacexapp.companyinfo.CompanyInfoMapper
import com.cotters.spacexapp.companyinfo.data.response.CompanyInfoDto
import com.cotters.spacexapp.companyinfo.data.database.CompanyInfo
import com.cotters.spacexapp.data.CompanyInfoCacheTimer
import com.cotters.spacexapp.data.SpaceXService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class CompanyInfoRepository @Inject constructor(
    private val service: SpaceXService,
    private val store: CompanyInfoStore,
    private val mapper: CompanyInfoMapper,
    private val cacheTimer: CompanyInfoCacheTimer,
) {

    suspend fun getCompanyInfo(): CompanyInfo? = withContext(Dispatchers.IO) {
        try {
            if (cacheTimer.shouldRequestCompanyInfo()) {
                val response = fetchCompanyInfo()
                val data = requireNotNull(response.body())
                val companyInfo = mapper.toStoreModel(data)
                store(companyInfo)
                cacheTimer.reset()
                companyInfo
            } else {
                store.get()
            }
        } catch (e: Exception) {
            cacheTimer.invalidate()
            store.get()
        }
    }

    private suspend fun fetchCompanyInfo(): Response<CompanyInfoDto> {
        return service.fetchCompanyInfo()
    }
}
