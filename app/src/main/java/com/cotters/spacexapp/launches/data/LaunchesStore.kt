package com.cotters.spacexapp.launches.data

import androidx.paging.PagingSource
import com.cotters.spacexapp.launches.data.database.Launch
import com.cotters.spacexapp.launches.data.database.LaunchesDao
import javax.inject.Inject


class LaunchesStore @Inject constructor(
    private val launchesDao: LaunchesDao,
) {

    fun storeAll(launches: List<Launch>) {
        launchesDao.insertAll(launches)
    }

    fun pagingSource(): PagingSource<Int, Launch> = launchesDao.pagingSource()

    fun lastUpdated() = 0 // TODO: Store in SharedPreferences
}
