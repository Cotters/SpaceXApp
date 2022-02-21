package com.cotters.spacexapp.companyinfo.domain.usecase

import com.cotters.spacexapp.service.SpaceXService
import com.cotters.spacexapp.companyinfo.domain.model.CompanyInfo
import javax.inject.Inject

class GetCompanyInfoUseCase @Inject constructor(private val service: SpaceXService) {

    suspend operator fun invoke(): CompanyInfo? {
        return try {
            val response = service.fetchCompanyInfo()
            with(requireNotNull(response.body())) {
                CompanyInfo(
                    name = name.orEmpty(),
                    founder = founder.orEmpty(),
                    foundedYear = founded ?: 0,
                    employees = employees ?: 0,
                    launchSites = launchSites ?: 0,
                    valuation = valuation ?: 0,
                    summary = summary.orEmpty()
                )
            }
        } catch (e: Throwable) {
            null
        }
    }

}
