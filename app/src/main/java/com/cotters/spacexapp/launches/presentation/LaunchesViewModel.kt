package com.cotters.spacexapp.launches.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.cotters.spacexapp.extensions.dateString
import com.cotters.spacexapp.extensions.timeString
import com.cotters.spacexapp.extensions.toLocalDate
import com.cotters.spacexapp.launches.domain.model.LaunchDomainModel
import com.cotters.spacexapp.launches.domain.usecase.GetLaunchesUseCase
import com.cotters.spacexapp.launches.presentation.model.LaunchDayDifference
import com.cotters.spacexapp.launches.presentation.model.LaunchStatus
import com.cotters.spacexapp.launches.presentation.model.LaunchUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class LaunchesViewModel @Inject constructor(
    getLaunches: GetLaunchesUseCase,
) : ViewModel() {
    
    val pagedLaunchesFlow = getLaunches.launchesPager
        .map(::convertToUiModel)
        .cachedIn(viewModelScope)

    private fun convertToUiModel(pagingData: PagingData<LaunchDomainModel>) =
        pagingData.map(::convertToLaunch)

    // TODO: Consider a mapper: testing and separation from VM.
    private fun convertToLaunch(launchModel: LaunchDomainModel) = LaunchUiModel(
        name = launchModel.name,
        date = launchModel.dateUnix.toMissionDateString(),
        // TODO: Use string resources (like Company Info text):
        rocketDetails = "${launchModel.rocket.name} ${launchModel.rocket.type}",
        status = getLaunchStatus(launchModel.success),
        links = launchModel.links,
        dayDifference = LaunchDayDifference(launchModel.dateUnix.daysDifferenceFromNow().toInt())
    )

    private fun getLaunchStatus(success: Boolean?): LaunchStatus {
        if (success == null) return LaunchStatus.Pending
        return if (success) LaunchStatus.Successful else LaunchStatus.Failed
    }

    private fun Long.toMissionDateString() =
        "${toLocalDate().dateString()} at ${toLocalDate().timeString()}"

    private fun Long.daysDifferenceFromNow(): Long {
        return (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - this) / dayInSeconds
    }

    companion object {
        const val dayInSeconds = 60 * 60 * 24
    }
}
