package com.aaron.employeedirectoryapp.navigation


sealed class AppScreens(
    val route: String
) {
    data object SplashScreen : AppScreens(AppRouteNames.SPLASH_SCREEN)
    data object AppHomeScreen : AppScreens(AppRouteNames.APP_HOME_SCREEN)
}