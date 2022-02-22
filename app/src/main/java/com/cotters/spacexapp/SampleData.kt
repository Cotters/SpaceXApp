package com.cotters.spacexapp

import com.cotters.spacexapp.companyinfo.data.response.CompanyInfoDto
import com.cotters.spacexapp.companyinfo.data.room.CompanyInfo
import com.cotters.spacexapp.companyinfo.domain.model.CompanyInfoDomainModel
import com.cotters.spacexapp.launches.data.response.*
import com.cotters.spacexapp.launches.data.room.Launch
import com.cotters.spacexapp.launches.data.room.LaunchLinks
import com.cotters.spacexapp.launches.data.room.Rocket
import com.cotters.spacexapp.launches.domain.model.LaunchDomainModel
import com.cotters.spacexapp.launches.domain.model.LaunchLinksDomainModel
import com.cotters.spacexapp.launches.domain.model.RocketDomainModel
import com.cotters.spacexapp.launches.presentation.model.LaunchUiModel
import com.cotters.spacexapp.launches.presentation.model.LaunchDayDifference
import com.cotters.spacexapp.launches.presentation.model.LaunchStatus

object SampleData {

    val companyInfoDto = CompanyInfoDto("SpaceX", "Elon Musk", 2002, 3500, 3, 7500000000, "Summary")

    val companyInfo = CompanyInfo("SpaceX", "Elon Musk", 2002, 3500, 3, 7500000000, "Summary")

    val companyInfoDomain = CompanyInfoDomainModel(
        name = "SpaceX",
        founder = "Elon Musk",
        foundedYear = 2002,
        employees = 10000,
        launchSites = 3,
        valuation = 52000000000L,
        summary = "SpaceX designs, manufactures and launches advanced rockets and spacecraft. The company was founded in 2002 to revolutionize space technology, with the ultimate goal of enabling people to live on other planets.",
    )

    const val companyInfoText =
        "SpaceX was founded by Elon Musk in 2002. It has 3500 employees and 3 launch sites. As of 2017 it has a valuation of USD $7,500,000,000."


    val launches: List<LaunchUiModel> = listOf(
        LaunchUiModel(
            name = "CRS-20",
            date = "2022/01/01 13:00",
            rocketDetails = "Falcon 9 / rocket",
            status = LaunchStatus.Successful,
            links = LaunchLinksDomainModel("https://images2.imgbox.com/3c/0e/T8iJcSN3_o.png",
                "https://article",
                "https://wiki",
                "https://webcast"),
            dayDifference = LaunchDayDifference(10),
        ),
        LaunchUiModel(
            name = "CRS-21",
            date = "2025/05/01 01:00",
            rocketDetails = "Falcon 9 / rocket",
            status = LaunchStatus.Pending,
            links = LaunchLinksDomainModel(null,
                "https://article",
                "https://wiki",
                "https://webcast"),
            dayDifference = LaunchDayDifference(-3),
        )
    )

    val domainLaunch = LaunchDomainModel(
        name = "CRS-20",
        dateUnix = 162346789160,
        rocket = RocketDomainModel("Falcon 9", "rocket"),
        success = true,
        links = LaunchLinksDomainModel(null,
            "https://article",
            "https://wiki",
            "https://webcast"),
    )

    val storedLaunch = Launch(
        missionName = "name",
        dateUnix = 1583556631L,
        rocket = Rocket("Falcon 9", "rocket"),
        success = true,
        links = LaunchLinks(
            patchImageUrl = "https://small",
            article = "https://article",
            wikipedia = "https://wiki",
            webcast = "https://webcast")
    )

    val domainLaunches = listOf(
        domainLaunch,
        domainLaunch.copy(name = "COTT-21"),
        domainLaunch.copy(name = "COTT-22"),
        domainLaunch.copy(name = "COTT-23"),
        domainLaunch.copy(name = "COTT-24"),
        domainLaunch.copy(name = "COTT-25"),
        domainLaunch.copy(name = "COTT-26"),
        domainLaunch.copy(name = "COTT-27"),
        domainLaunch.copy(name = "COTT-28"),
        domainLaunch.copy(name = "COTT-29"),
    )

    private fun createDto(
        name: String = "CRS-20",
        success: Boolean? = true,
        unixDate: Long = 1583556631L,
        launchWebLinks: LaunchLinksDto = LaunchLinksDto(null,
            "https://article",
            "https://wiki",
            "https://webcast"),
    ) = LaunchDto(
        name = name,
        dateUnix = unixDate,
        rocket = RocketDto("Falcon 9", "rocket"),
        success = success,
        links = LaunchLinksDto(
            patch = LaunchPatchLinksDto("https://small", "https://large"),
            launchWebLinks.article,
            launchWebLinks.wikipedia,
            launchWebLinks.webcast
        )
    )

    val launchDataResponse = listOf(
        createDto(name = "Test-01"),
        createDto(success = true),
        createDto(success = false),
        createDto(unixDate = 15835566301L),
        createDto(name = "Test-02"),
    )
}
