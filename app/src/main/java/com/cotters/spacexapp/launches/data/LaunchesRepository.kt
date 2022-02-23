package com.cotters.spacexapp.launches.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.cotters.spacexapp.data.SpaceXService
import com.cotters.spacexapp.launches.data.database.Launch
import com.cotters.spacexapp.launches.data.database.LaunchesCacheTimer
import com.cotters.spacexapp.launches.data.request.LaunchesRequestBody
import com.cotters.spacexapp.launches.data.request.LaunchesRequestOptions
import com.cotters.spacexapp.launches.domain.LaunchesObjectMapper
import com.cotters.spacexapp.ui.values.Dimensions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class LaunchesRepository @Inject constructor(
    private val service: SpaceXService,
    private val launchesStore: LaunchesStore,
    private val mapper: LaunchesObjectMapper = LaunchesObjectMapper(),
    private val cacheTimer: LaunchesCacheTimer,
) : RemoteMediator<Int, Launch>() {

    /** ----------------------------------- Paging Source ----------------------------------- **/

    override suspend fun initialize(): InitializeAction {
        return if (cacheTimer.shouldRequestLaunches()) {
            // Need to refresh cached data from network; returning LAUNCH_INITIAL_REFRESH here
            // will also block RemoteMediator's APPEND and PREPEND from running until REFRESH
            // succeeds.
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            // Cached data is up-to-date, so there is no need to re-fetch from network.
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    private var currentPage = initialPage

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Launch>,
    ): MediatorResult = withContext(Dispatchers.IO) {
        try {

            val request = LaunchesRequestBody(
                options = LaunchesRequestOptions(itemLimit, currentPage, fieldsToPopulate)
            )
            val response = service.fetchLaunches(request).body()
            currentPage = response?.nextPage ?: initialPage
            val dataLaunches = response?.items.orEmpty()
            val launchesToStore = mapper.dataToStore(dataLaunches)
            launchesStore.storeAll(launchesToStore)

            val endOfPaginationReached = dataLaunches.isEmpty() || response?.hasNextPage == false
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    companion object {
        private const val initialPage = 1
        private const val itemLimit = Dimensions.paginationSize
        // TODO: Pass in as class parameter and test request and response body.
        private val fieldsToPopulate = listOf("rocket")
    }
}
