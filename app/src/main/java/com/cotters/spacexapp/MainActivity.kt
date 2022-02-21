package com.cotters.spacexapp

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.cotters.spacexapp.companyinfo.presentation.CompanyInfoCard
import com.cotters.spacexapp.companyinfo.presentation.CompanyInfoViewModel
import com.cotters.spacexapp.launches.presentation.LaunchCard
import com.cotters.spacexapp.launches.presentation.LaunchesViewModel
import com.cotters.spacexapp.launches.presentation.model.LaunchUiModel
import com.cotters.spacexapp.ui.theme.SpaceXAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

@ExperimentalMaterialApi
@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var networkManager: NetworkManager

    private val companyInfoViewModel by viewModels<CompanyInfoViewModel>()
    private val launchesViewModel by viewModels<LaunchesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkManager = NetworkManager(getSystemService(ConnectivityManager::class.java))
        networkManager.listenToNetworkEvents()
        val connectedState = networkManager.hasNetwork

        setContent {
            SpaceXAppTheme {
                PagedAppScreen(
                    companyInfo = companyInfoViewModel.companyInfo.value,
                    launchesFlow = launchesViewModel.pagedLaunchesFlow,
                    connected = connectedState
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun PagedAppScreen(
    companyInfo: String,
    launchesFlow: Flow<PagingData<LaunchUiModel>>,
    connected: State<Boolean>,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "SpaceX")
                }
            )
        },
        content = { padding ->
            MainScreen(launchesFlow = launchesFlow,
                padding = padding,
                companyInfo = companyInfo)
        },
    )
}


//@ExperimentalMaterialApi
//@Composable
//fun AppScreen(
//    companyInfo: String,
//    launchesFlow: Flow<List<LaunchUiModel>>,
//    connected: State<Boolean>,
//) {
//    val scaffoldState = rememberScaffoldState()
//    val scope = rememberCoroutineScope()
//    val connectionErrorMessage = stringResource(R.string.connection_error_message)
//
//    if (!connected.value) {
//        LaunchedEffect(key1 = connected.value) {
//            scope.launch {
//                scaffoldState.snackbarHostState.showSnackbar(
//                    message = connectionErrorMessage,
//                    duration = SnackbarDuration.Long
//                )
//            }
//        }
//    }
//
//    Scaffold(
//        scaffoldState = scaffoldState,
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(text = "SpaceX")
//                }
//            )
//        },
//        content = { padding ->
//            ClientScreen(
//                padding = padding,
//                companyInfo = companyInfo,
//                launchesFlow = launchesFlow,
//            )
//        },
//    )
//}

@ExperimentalMaterialApi
@Composable
fun MainScreen(
    launchesFlow: Flow<PagingData<LaunchUiModel>>,
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    companyInfo: String,
) = Surface(color = MaterialTheme.colors.background) {

    val launches = launchesFlow.collectAsLazyPagingItems()
//    val launches = launchesFlow.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = modifier
            .padding(padding)
            .padding(horizontal = 5.dp),
    ) {
        item {
            Text(text = "Company Info", style = MaterialTheme.typography.h5)
            CompanyInfoCard(companyInfo)
            Text(text = "Launches", style = MaterialTheme.typography.h5)
        }
        items(launches.itemCount) { index ->
            launches[index]?.let { LaunchCard(it) }
        }
//        items(launches.value) {
//            LaunchCard(it)
//        }
    }
}


//@ExperimentalMaterialApi
//@Preview(
//    uiMode = UI_MODE_NIGHT_YES
//)
//@Composable
//private fun PreviewApp() {
//    SpaceXAppTheme {
//        AppScreen(
//            companyInfo = SampleData.companyInfo,
//            launchesFlow = flowOf(),
//            connected = derivedStateOf { true }
//        )
//    }
//}
