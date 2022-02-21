package com.cotters.spacexapp.companyinfo.data

import com.cotters.spacexapp.service.SpaceXService
import javax.inject.Inject

class CompanyInfoRepository @Inject constructor(
    val service: SpaceXService,
) {

    suspend fun updateCompanyInfo() {
        service.fetchCompanyInfo()
    }
}
