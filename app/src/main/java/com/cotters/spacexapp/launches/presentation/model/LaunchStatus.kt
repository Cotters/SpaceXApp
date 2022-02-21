package com.cotters.spacexapp.launches.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class LaunchStatus(val icon: ImageVector, val iconTint: Color) {
    Pending(Icons.Filled.Schedule, Color.LightGray),
    Successful(Icons.Filled.Done, Color.Green),
    Failed(Icons.Filled.Close, Color.Red);
}
