package com.aaron.employeedirectoryapp.ui.screens.apphome

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aaron.employeedirectoryapp.R
import com.aaron.employeedirectoryapp.ui.common.compose.CommonButton
import com.aaron.employeedirectoryapp.ui.common.compose.CommonCardContent
import com.aaron.employeedirectoryapp.ui.common.compose.CommonText
import com.aaron.employeedirectoryapp.ui.common.compose.NetworkErrorDialog
import com.aaron.employeedirectoryapp.ui.common.composeattributes.TextAttributes
import com.aaron.employeedirectoryapp.ui.common.screenstate.UIState
import com.aaron.employeedirectoryapp.ui.theme.CustomTheme
import kotlinx.coroutines.flow.flow

@Composable
fun AppHomeScreen(
    navController: NavController,
    progressState: State<UIState>,
    screeState: State<AppHomeScreenUIStates>,
    onEvent: (AppHomeScreenUIEvents) -> Unit
) {
    LaunchedEffect(Unit) {
        onEvent.invoke(AppHomeScreenUIEvents.GetInitialData)
    }
    CustomTheme {
        val listState = rememberLazyListState(initialFirstVisibleItemIndex = 0)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CustomTheme.colors.cardDefault)
        ) {
            if (
                screeState.value.isEmployeeDirectoryNetworkError.value
                || screeState.value.isEmployeeDirectoryEmptyUnknownError.value

            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    NetworkErrorDialog(
                        showDialog = remember {
                            mutableStateOf(true)
                        },
                        buttonLabel = screeState.value.buttonLabel,
                        headerText = screeState.value.employeeDirectoryEmptyText,
                        tryAgain = {
                            onEvent.invoke(
                                AppHomeScreenUIEvents.RefreshApiCall
                            )
                        }
                    )
                }

            }
            Column(
                modifier = Modifier
                    .background(CustomTheme.colors.cardDefault)
                    .padding(
                        start = CustomTheme.spaces.dp16,
                        end = CustomTheme.spaces.dp16,
                        bottom = CustomTheme.spaces.dp100
                    )
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                LazyColumn(state = listState) {
                    item {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(CustomTheme.spaces.dp16)
                        )
                    }
                    if (screeState.value.isEmployeeDirectoryEmpty.value) {
                        item {
                            CommonCardContent(
                                biography = screeState.value.employeeDirectoryEmptyText,
                                cardImage = screeState.value.placeHolder.toString()
                            )
                        }
                    } else {
                        items(items = screeState.value.employeeDirectoryList.toList()) { item ->
                            val imageUrl = item.photoUrlLarge.takeIf { it.isNotEmpty() }
                                ?: item.photoUrlSmall.takeIf { it.isNotEmpty() }
                            CommonCardContent(
                                fullName = item.fullName,
                                team = item.team,
                                biography = item.biography,
                                cardImage = imageUrl!!
                            )
                        }
                    }
                }


            }
            if (progressState.value == UIState.Loading) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                ) {
                    CommonText(
                        TextAttributes(
                            text = screeState.value.progressLoadingText,
                            textAlign = TextAlign.Center,
                            textColor = CustomTheme.colors.text0,
                            textStyle = CustomTheme.typography.titleBold
                        )
                    )
                    CircularProgressIndicator()
                }
            }

            if (screeState.value.employeeDirectoryList.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = CustomTheme.spaces.dp16)
                        .align(Alignment.BottomCenter),
                ) {
                    CommonButton(
                        buttonTitle = screeState.value.buttonText
                    ) {
                        onEvent.invoke(AppHomeScreenUIEvents.RefreshApiCall)
                    }
                }
            }
        }


    }
}

@Composable
@Preview(
    name = "Light Mode",
    showBackground = true,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
private fun PreviewAppHomeScreen() {
    AppHomeScreen(
        navController = rememberNavController(),
        progressState = flow<UIState> {}.collectAsState(initial = UIState.Success),
        screeState = flow<AppHomeScreenUIStates> {}.collectAsState(
            initial = AppHomeScreenUIStates(
                employeeDirectoryEmptyText = stringResource(R.string.employee_directory_empty_text),
                placeHolder = R.drawable.place_holder,
                isEmployeeDirectoryEmpty = remember {
                    mutableStateOf(true)
                }
            )
        ),
        onEvent = {}
    )
}