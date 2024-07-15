package com.aaron.employeedirectoryapp.ui.screens.splashscreen

import com.aaron.employeedirectoryapp.R
import com.aaron.employeedirectoryapp.data.DataRepository
import com.aaron.employeedirectoryapp.di.appresources.AppResources
import com.aaron.employeedirectoryapp.framework.base.viewmodel.BaseViewModel
import com.aaron.employeedirectoryapp.ui.common.screenstate.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val appResources: AppResources,
    private val dataRepository: DataRepository
):BaseViewModel(dataRepository){
    private val _screenState = MutableStateFlow(getUiInitialState())
     val screenState = _screenState.asStateFlow()
    private val _progressState = MutableStateFlow<UIState>(UIState.Success)
    val progressState = _progressState.asStateFlow()

    fun getUiInitialState():SplashScreenUIStates{
        return SplashScreenUIStates(
            appLogo = appResources.getString(R.string.app_logo),
            appHeaderText = appResources.getString(R.string.app_header_text)
        )
    }
    fun onSplashScreenEvent(events:SplashScreenUIEvents){
        when(events){
            is SplashScreenUIEvents.GetInitialData->{}
        }
    }
}