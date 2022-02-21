package com.cotters.spacexapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cotters.spacexapp.launches.data.room.Launch
import com.cotters.spacexapp.launches.data.room.LaunchesDao

@Database(version = 1, entities = [Launch::class])
abstract class SpaceXDatabase : RoomDatabase() {

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

