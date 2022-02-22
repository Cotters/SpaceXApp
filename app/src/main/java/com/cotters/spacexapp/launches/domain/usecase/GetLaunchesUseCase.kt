package com.cotters.spacexapp.launches.domain.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.cotters.spacexapp.launches.data.LaunchesRepository
import com.cotters.spacexapp.launches.domain.LaunchesObjectMapper
import com.cotters.spacexapp.launches.data.LaunchesStore
import com.cotters.spacexapp.ui.values.Dimensions
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetLaunchesUseCase @Inject constructor(
    private val launchesStore: LaunchesStore,
    launchesRepository: LaunchesRepository,
    private val mapper: LaunchesObjectMapper = LaunchesObjectMapper(),
) {

    @ExperimentalPagingApi
    val launchesPager = Pager(
        config = PagingConfig(Dimensions.paginationSize),
        remoteMediator = launchesRepository
    ) {
        launchesStore.pagingSource()
    }
        .flow
        .map(mapper::storedToDomain)
}
