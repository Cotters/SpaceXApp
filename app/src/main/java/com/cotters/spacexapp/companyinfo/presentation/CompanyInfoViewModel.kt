package com.cotters.spacexapp.companyinfo.presentation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cotters.spacexapp.R
import com.cotters.spacexapp.companyinfo.domain.model.CompanyInfoDomainModel
import com.cotters.spacexapp.companyinfo.domain.usecase.GetCompanyInfoUseCase
import com.cotters.spacexapp.extensions.toDollarString
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class CompanyInfoViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getCompanyInfo: GetCompanyInfoUseCase,
) : ViewModel() {

    var companyInfo = mutableStateOf(context.getString(R.string.companyInfoLoadingText))
        private set

    init {
        updateCompanyInfo()
    }

    private fun updateCompanyInfo() = viewModelScope.launch {
        getCompanyInfo()?.let(::updateCompanyInfoText) ?: showError()
    }

    private fun updateCompanyInfoText(model: CompanyInfoDomainModel) = with(model) {
        companyInfo.value = context.getString(R.string.companyInfoTemplate).format(
            name,
            founder,
            foundedYear,
            employees,
            launchSites,
            valuation.toDollarString()
        )
    }

    private fun showError() {
        companyInfo.value = context.getString(R.string.failedErrorMessage)
    }
}
