package com.cotters.spacexapp.launches.data

import androidx.paging.PagingSource
import androidx.room.Query
import com.cotters.spacexapp.launches.data.room.Launch
import com.cotters.spacexapp.launches.data.room.LaunchesDao
import kotlinx.coroutines.flow.Flow
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
