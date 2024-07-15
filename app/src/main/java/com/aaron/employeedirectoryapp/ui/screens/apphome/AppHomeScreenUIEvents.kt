package com.aaron.employeedirectoryapp.ui.screens.apphome


sealed class AppHomeScreenUIEvents{
    data object GetInitialData: AppHomeScreenUIEvents()
    data object RefreshApiCall:AppHomeScreenUIEvents()
}