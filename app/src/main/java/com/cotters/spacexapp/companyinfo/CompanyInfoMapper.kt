package com.cotters.spacexapp.companyinfo

import com.cotters.spacexapp.companyinfo.data.response.CompanyInfoDto
import com.cotters.spacexapp.companyinfo.data.room.CompanyInfo
import com.cotters.spacexapp.companyinfo.domain.model.CompanyInfoDomainModel

class CompanyInfoMapper {
    fun toStoreModel(companyInfoDto: CompanyInfoDto): CompanyInfo = with(companyInfoDto) {
        return CompanyInfo(
            name = name.orEmpty(),
            founder = founder.orEmpty(),
            foundedYear = founded ?: 0,
            employees = employees ?: 0,
            launchSites = launchSites ?: 0,
            valuation = valuation ?: 0,
            summary = summary.orEmpty(),
        )
    }

    fun toDomainModel(companyInfo: CompanyInfo): CompanyInfoDomainModel = with(companyInfo) {
        CompanyInfoDomainModel(
            name = name,
            founder = founder,
            foundedYear = foundedYear,
            employees = employees,
            launchSites = launchSites,
            valuation = valuation,
            summary = summary
        )
    }
}
