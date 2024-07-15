package com.aaron.employeedirectoryapp.ui.screens.apphome

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.aaron.employeedirectoryapp.data.remote.models.responses.Employee
import com.aaron.employeedirectoryapp.utilities.constants.AppConstants

data class AppHomeScreenUIStates(
    val employeeDirectoryEmptyText:String = AppConstants.EMPTY_STRING,
    val employeeDirectoryList:MutableList<Employee> = mutableListOf(),
    val isEmployeeDirectoryEmpty:MutableState<Boolean> = mutableStateOf(false),
    val isEmployeeDirectoryNetworkError:MutableState<Boolean> = mutableStateOf(false),
    val isEmployeeDirectoryEmptyUnknownError:MutableState<Boolean> = mutableStateOf(false),
    val placeHolder:Int? = null,
    val buttonText:String = AppConstants.EMPTY_STRING,
    val buttonLabel:String = AppConstants.EMPTY_STRING,
    val progressLoadingText:String = AppConstants.EMPTY_STRING,
)