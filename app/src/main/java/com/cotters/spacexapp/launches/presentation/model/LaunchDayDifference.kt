package com.cotters.spacexapp.launches.presentation.model

data class LaunchDayDifference(
    val daysTilLaunch: Int
) {
    val title = if (daysTilLaunch < 0) "Days until" else "Days since"
    val text = daysTilLaunch.toString()
}
