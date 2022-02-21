package com.cotters.spacexapp.launches.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.cotters.spacexapp.launches.data.response.LaunchDto
import com.cotters.spacexapp.launches.data.room.Launch
import com.cotters.spacexapp.launches.data.room.LaunchLinks
import com.cotters.spacexapp.launches.data.room.Rocket
import com.cotters.spacexapp.launches.domain.model.LaunchDomainModel
import com.cotters.spacexapp.launches.domain.model.LaunchLinksDomainModel
import com.cotters.spacexapp.launches.domain.model.RocketDomainModel
import javax.inject.Inject

class LaunchesObjectMapper @Inject constructor() {

    fun dataToStore(dataLaunches: List<LaunchDto?>) = dataLaunches.mapNotNull(::toStoreModel)

    fun storedToDomain(pagingData: PagingData<Launch>) = pagingData.map(::toDomainModel)

    private fun toDomainModel(launch: Launch?): LaunchDomainModel {
        return LaunchDomainModel(
            name = launch?.missionName.orEmpty(),
            dateUnix = launch?.dateUnix ?: 0L,
            rocket = RocketDomainModel(
                name = launch?.rocket?.name.orEmpty(),
                type = launch?.rocket?.type.orEmpty()
            ),
            success = launch?.success,
            links = LaunchLinksDomainModel(
                patchImageUrl = launch?.links?.patchImageUrl,
                article = launch?.links?.article,
                wikipedia = launch?.links?.wikipedia,
                webcast = launch?.links?.webcast)
        )
    }

    private fun toStoreModel(launchDto: LaunchDto?): Launch {
        return Launch(
            missionName = launchDto?.name.orEmpty(),
            dateUnix = launchDto?.dateUnix ?: 0L,
            rocket = Rocket(
                name = launchDto?.rocket?.name.orEmpty(),
                type = launchDto?.rocket?.type.orEmpty()
            ),
            success = launchDto?.success,
            links = LaunchLinks(
                patchImageUrl = launchDto?.links?.patch?.small,
                article = launchDto?.links?.article,
                wikipedia = launchDto?.links?.wikipedia,
                webcast = launchDto?.links?.webcast
            )
        )
    }

}
