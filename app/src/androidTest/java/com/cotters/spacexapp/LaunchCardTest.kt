package com.cotters.spacexapp

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.cotters.spacexapp.launches.presentation.LaunchCard
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterialApi
class LaunchCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun showsLaunchPatchImageView() {
        composeTestRule.setContent {
            LaunchCard(launch = SampleData.launches.first())
        }

        composeTestRule.onNodeWithContentDescription("Launch patch image").assertIsDisplayed()
    }

    @Test
    fun showsLaunchDetails() {
        val launch = SampleData.launches.first()
        composeTestRule.setContent {
            LaunchCard(launch = launch)
        }

        composeTestRule.onNodeWithText(launch.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(launch.date).assertIsDisplayed()
        composeTestRule.onNodeWithText(launch.rocketDetails).assertIsDisplayed()
        composeTestRule.onNodeWithText(launch.dayDifference.text).assertIsDisplayed()
    }

}
