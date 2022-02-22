package com.cotters.spacexapp.companyinfo.data

import com.cotters.spacexapp.companyinfo.data.room.CompanyInfo
import javax.inject.Inject

class CompanyInfoStore @Inject constructor(

) {

    operator fun invoke(companyInfo: CompanyInfo) {

    }

    suspend fun getCompanyInfo(): CompanyInfo? {
        return null
    }
}
