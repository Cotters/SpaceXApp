package com.cotters.spacexapp.companyinfo.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cotters.spacexapp.ui.theme.SpaceXAppTheme


@Composable
fun CompanyInfoCard(companyInfo: String) = Card(
    backgroundColor = MaterialTheme.colors.background,
    elevation = 2.dp,
    modifier = Modifier.padding(vertical = 3.dp).fillMaxWidth()
) {
    Text(
        text = companyInfo,
        style = MaterialTheme.typography.body1,
        modifier = Modifier.padding(all = 5.dp)
    )
}

@Preview
@Composable
private fun PreviewCompanyInfoCard() {
    SpaceXAppTheme {
        CompanyInfoCard(companyInfo = "SpaceX is a company founded by Elon Musk in 2002.")
    }
}
