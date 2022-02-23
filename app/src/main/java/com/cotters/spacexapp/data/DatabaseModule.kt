package com.cotters.spacexapp.data

import android.content.Context
import androidx.room.Room
import com.cotters.spacexapp.companyinfo.data.room.CompanyInfoDao
import com.cotters.spacexapp.launches.data.room.LaunchesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): SpaceXDatabase {
        return Room.databaseBuilder(
            appContext,
            SpaceXDatabase::class.java, "SpaceXApp.db"
        ).build()
    }

    @Provides
    fun provideCompanyInfoDao(database: SpaceXDatabase): CompanyInfoDao {
        return database.companyInfoDao()
    }

    @Provides
    fun provideLaunchesDao(database: SpaceXDatabase): LaunchesDao {
        return database.launchesDao()
    }
}
