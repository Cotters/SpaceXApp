package com.cotters.spacexapp.launches.data.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launches")
data class Launch(
    @PrimaryKey @ColumnInfo val missionName: String,
    @ColumnInfo val dateUnix: Long,
    @Embedded val rocket: Rocket?,
    @ColumnInfo val success: Boolean?,
    @Embedded val links: LaunchLinks?,
)
