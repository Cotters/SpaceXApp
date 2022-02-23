package com.cotters.spacexapp.companyinfo.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class CompanyInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(companyInfo: CompanyInfo)

    @Query("SELECT * FROM company_info")
    abstract fun get(): CompanyInfo?

}
