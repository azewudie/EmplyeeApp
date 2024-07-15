package com.aaron.employeedirectoryapp.navigation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aaron.employeedirectoryapp.ui.screens.apphome.AppHomeScreen
import com.aaron.employeedirectoryapp.ui.screens.apphome.AppHomeScreenViewModel
import com.aaron.employeedirectoryapp.ui.screens.splashscreen.SplashScreen
import com.aaron.employeedirectoryapp.ui.screens.splashscreen.SplashScreenViewModel
import com.aaron.employeedirectoryapp.ui.theme.CustomTheme
import com.aaron.employeedirectoryapp.ui.theme.LocalTheme
import com.aaron.employeedirectoryapp.utilities.constants.AppConstants

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    var currentScreen: String = AppScreens.AppHomeScreen.route
    backStackEntry?.destination?.route?.let {
        currentScreen = it
    }
    var showTopAppBar by rememberSaveable {
        mutableStateOf(false)
    }
    showTopAppBar =
        when (backStackEntry?.destination?.route) {
            AppScreens.AppHomeScreen.route -> true
            else -> false
        }
    val vmStoreOwner = rememberViewModelStoreOwner()
    Scaffold(
        containerColor = CustomTheme.colors.cardDefault,
        topBar = {
            if (showTopAppBar) {
                CommonAppBar(
                    currentScreen = getScreenTitle(currentScreen),
                )
            }

        }
    ) { innerProvide ->
        CompositionLocalProvider(LocalNavGraphViewModelStoreOwner provides vmStoreOwner) {
            NavHost(
                navController = navController,
                startDestination = AppScreens.SplashScreen.route,
                modifier = Modifier.padding(innerProvide)
            ) {
                composable(AppScreens.SplashScreen.route) {
                    val viewModel = hiltViewModel<SplashScreenViewModel>()
                    val screenState = viewModel.screenState.collectAsStateWithLifecycle()
                    val progressState = viewModel.progressState.collectAsStateWithLifecycle()
                    SplashScreen(
                        navController = navController,
                        progressState = progressState,
                        screeState = screenState,
                        onEvent = viewModel::onSplashScreenEvent,
                    )
                }
                composable(AppScreens.AppHomeScreen.route) {
                    val viewModel = hiltViewModel<AppHomeScreenViewModel>()
                    val screenState = viewModel.screenState.collectAsStateWithLifecycle()
                    val progressState = viewModel.progressState.collectAsStateWithLifecycle()
                    AppHomeScreen(
                        navController = navController,
                        progressState = progressState,
                        screeState = screenState,
                        onEvent = viewModel::onAppHomeScreenEvent
                    )
                }
            }
        }

    }

}

@Composable
fun rememberViewModelStoreOwner(): ViewModelStoreOwner {
    val context = LocalContext.current
    return remember(context) {
        context as ViewModelStoreOwner
    }
}

private fun getScreenTitle(appRoute: String): String {
    var title = AppConstants.EMPTY_STRING
    when (appRoute) {
        AppRouteNames.APP_HOME_SCREEN -> title = ScreenTitle.TITLE_HOME
        else -> {}
    }
    return title
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun CommonAppBar(
    currentScreen: String,
) {
    var isClicked by remember {
        mutableStateOf(false)
    }
    LocalTheme.current.isDark = isClicked
    CustomTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = CustomTheme.colors.cardDefault)
                .clickable { isClicked = !isClicked }
        ) {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = CustomTheme.spaces.dp0),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CustomTheme.colors.logoColor,
                    titleContentColor = CustomTheme.colors.textWhite,
                    actionIconContentColor = Color.Unspecified,
                    navigationIconContentColor = Color.Unspecified,
                    scrolledContainerColor = Color.Unspecified,
                ),
                title = {
                    Row {
                        Text(
                            text = currentScreen,
                            style = CustomTheme.typography.title0Regular,
                            modifier = Modifier.padding(vertical = CustomTheme.spaces.dp16)
                        )
                    }
                },
                navigationIcon = {},
                actions = { },
                scrollBehavior = null
            )
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
private fun PreviewCommonAppBar() {
    CustomTheme {
        CommonAppBar(
            currentScreen = ScreenTitle.TITLE_HOME
        )
    }
}

val LocalNavGraphViewModelStoreOwner =
    staticCompositionLocalOf<ViewModelStoreOwner> {
        TODO("Undefined")
    }