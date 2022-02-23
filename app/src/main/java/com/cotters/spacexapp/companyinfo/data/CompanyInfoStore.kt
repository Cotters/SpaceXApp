package com.cotters.spacexapp.companyinfo.data

import com.cotters.spacexapp.companyinfo.data.database.CompanyInfo
import com.cotters.spacexapp.companyinfo.data.database.CompanyInfoDao
import javax.inject.Inject

class CompanyInfoStore @Inject constructor(
    private val companyInfoDao: CompanyInfoDao,
) {

    operator fun invoke(companyInfo: CompanyInfo) {
        companyInfoDao.insert(companyInfo)
    }

    fun get() = companyInfoDao.get()
}
