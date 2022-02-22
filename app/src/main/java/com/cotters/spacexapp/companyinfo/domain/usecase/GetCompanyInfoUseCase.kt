package com.cotters.spacexapp.companyinfo.domain.usecase

import com.cotters.spacexapp.companyinfo.CompanyInfoMapper
import com.cotters.spacexapp.companyinfo.data.CompanyInfoRepository
import javax.inject.Inject

class GetCompanyInfoUseCase @Inject constructor(
    private val repository: CompanyInfoRepository,
    private val mapper: CompanyInfoMapper,
) {
    suspend operator fun invoke() = repository.getCompanyInfo()?.let(mapper::toDomainModel)
}
