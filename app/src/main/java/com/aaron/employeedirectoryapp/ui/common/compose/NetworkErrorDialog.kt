package com.aaron.employeedirectoryapp.ui.common.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.aaron.employeedirectoryapp.ui.theme.CustomTheme
import com.aaron.employeedirectoryapp.utilities.constants.AppConstants

@Composable
fun NetworkErrorDialog(
    showDialog:MutableState<Boolean> = mutableStateOf(false),
    headerText:String = AppConstants.EMPTY_STRING,
    buttonLabel:String = AppConstants.EMPTY_STRING,
    tryAgain:() -> Unit
){
   CustomTheme {
       CommonAppDialog(
           showDialog = showDialog,
           onConfirm = tryAgain,
           headerText = headerText,
           buttonLabel = buttonLabel
       )
   }
}