package com.cotters.spacexapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.cotters.spacexapp.companyinfo.presentation.CompanyInfoCard
import org.junit.Rule
import org.junit.Test

class CompanyInfoViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun showsCompanyInfoText() {
        val companyInfo = "Testing that this string gets displayed!"

        composeTestRule.setContent {
            CompanyInfoCard(companyInfo = companyInfo)
        }

        composeTestRule.onNodeWithText(companyInfo).assertExists().assertIsDisplayed()
    }
}
