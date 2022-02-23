package com.cotters.spacexapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cotters.spacexapp.companyinfo.data.database.CompanyInfo
import com.cotters.spacexapp.companyinfo.data.database.CompanyInfoDao
import com.cotters.spacexapp.launches.data.database.Launch
import com.cotters.spacexapp.launches.data.database.LaunchesDao

@Database(version = 1, entities = [CompanyInfo::class, Launch::class])
abstract class SpaceXDatabase : RoomDatabase() {

    abstract fun companyInfoDao(): CompanyInfoDao

    abstract fun launchesDao(): LaunchesDao

    companion object {

        @Volatile
        private var INSTANCE: RoomDatabase? = null

        fun getInstance(context: Context): RoomDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                SpaceXDatabase::class.java, "SpaceXApp.db"
            ).build()
    }
}

