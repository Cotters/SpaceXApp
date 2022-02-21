package com.cotters.spacexapp.launches.presentation.model

import com.cotters.spacexapp.launches.domain.model.LaunchLinksDomainModel

class LaunchUiModel(
    val name: String,
    val date: String,
    val rocketDetails: String,
    val status: LaunchStatus,
    val links: LaunchLinksDomainModel,
    val dayDifference: LaunchDayDifference
)
