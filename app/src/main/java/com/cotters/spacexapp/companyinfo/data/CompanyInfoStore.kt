package com.cotters.spacexapp.companyinfo.data

import com.cotters.spacexapp.companyinfo.data.room.CompanyInfo
import com.cotters.spacexapp.companyinfo.data.room.CompanyInfoDao
import javax.inject.Inject

class CompanyInfoStore @Inject constructor(
    private val companyInfoDao: CompanyInfoDao,
) {

    operator fun invoke(companyInfo: CompanyInfo) {
        companyInfoDao.insert(companyInfo)
    }

    fun get() = companyInfoDao.get()
}
