package com.cotters.spacexapp.companyinfo.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company_info")
data class CompanyInfo(
    @PrimaryKey @ColumnInfo val name: String,
    @ColumnInfo val founder: String,
    @ColumnInfo val foundedYear: Int,
    @ColumnInfo val employees: Int,
    @ColumnInfo val launchSites: Int,
    @ColumnInfo val valuation: Long,
    @ColumnInfo val summary: String,
)
