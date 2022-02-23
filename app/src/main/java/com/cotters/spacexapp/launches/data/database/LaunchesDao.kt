package com.cotters.spacexapp.launches.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LaunchesDao {

    @Query("SELECT * FROM launches ORDER BY dateUnix ASC")
    abstract fun getAll(): Flow<List<Launch>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(launches: List<Launch>)

    @Query("SELECT * FROM launches ORDER BY dateUnix ASC")
    abstract fun pagingSource(): PagingSource<Int, Launch>
}
