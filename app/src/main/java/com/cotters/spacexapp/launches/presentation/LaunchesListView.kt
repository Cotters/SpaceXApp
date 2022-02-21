package com.cotters.spacexapp.launches.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.cotters.spacexapp.R
import com.cotters.spacexapp.SampleData
import com.cotters.spacexapp.launches.presentation.model.LaunchUiModel
import com.cotters.spacexapp.launches.presentation.model.LaunchStatus
import com.cotters.spacexapp.ui.theme.SpaceXAppTheme
import com.cotters.spacexapp.ui.values.Dimensions
import com.cotters.spacexapp.ui.values.Strings

@ExperimentalMaterialApi
@Composable
fun LaunchCard(launch: LaunchUiModel) {
    val patchImageSize = Dimensions.patchImageSize
    Card(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
    ) {
        ListItem(
            icon = { LaunchPatchImage(launch.links.patchImageUrl, patchImageSize) },
            trailing = { LaunchSuccessImage(status = launch.status) },
        ) {
            MissionDetails(launch)
        }
    }
}

@Composable
private fun LaunchPatchImage(imageURL: String?, imageSize: Int) {
    Image(
        painter = rememberImagePainter(
            data = imageURL,
            builder = {
                size(imageSize, imageSize)
                placeholder(R.drawable.ic_image_placeholder)
            }
        ),
        contentDescription = stringResource(R.string.patch_image_content_description),
        contentScale = ContentScale.Fit,
    )
}

@Composable
private fun MissionDetails(launch: LaunchUiModel) = Column(
    modifier = Modifier.padding(vertical = 5.dp)
) {
    MissionDetailRow(Strings.missionName, launch.name)
    MissionDetailRow(Strings.dateTime, launch.date)
    MissionDetailRow(Strings.rocket, launch.rocketDetails)
    MissionDetailRow(launch.dayDifference.title, launch.dayDifference.text)
}

@Composable
fun MissionDetailRow(key: String, value: String) = Row {
    val captionStyle = MaterialTheme.typography.caption
    Text(text = "${key}:", maxLines = 1, style = captionStyle, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.weight(1f))
    Text(text = value, maxLines = 1, style = captionStyle)
}

@Composable
private fun LaunchSuccessImage(status: LaunchStatus) {
    Box(Modifier.size(20.dp)) {
        Icon(
            imageVector = status.icon,
            contentDescription = "Launch success status icon",
            tint = status.iconTint,
            modifier = Modifier.size(20.dp),
        )
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
private fun PreviewListOfLaunchCard() {
    SpaceXAppTheme {
        LazyColumn {
            items(SampleData.launches) {
                LaunchCard(it)
            }
        }
    }
}
