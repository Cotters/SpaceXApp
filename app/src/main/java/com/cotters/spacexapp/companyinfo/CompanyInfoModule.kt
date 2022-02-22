package com.cotters.spacexapp.companyinfo

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object CompanyInfoModule {
    @Provides
    fun provideCompanyInfoMapper() = CompanyInfoMapper()
}