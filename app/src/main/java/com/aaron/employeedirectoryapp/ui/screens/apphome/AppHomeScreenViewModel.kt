package com.aaron.employeedirectoryapp.ui.screens.apphome

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.aaron.employeedirectoryapp.R
import com.aaron.employeedirectoryapp.data.DataRepository
import com.aaron.employeedirectoryapp.data.remote.MainResponse
import com.aaron.employeedirectoryapp.data.remote.models.responses.Employee
import com.aaron.employeedirectoryapp.data.remote.models.responses.EmployeeMalFormedResponse
import com.aaron.employeedirectoryapp.data.remote.models.responses.EmployeeResponse
import com.aaron.employeedirectoryapp.di.appresources.AppResources
import com.aaron.employeedirectoryapp.framework.base.viewmodel.BaseViewModel
import com.aaron.employeedirectoryapp.ui.common.screenstate.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppHomeScreenViewModel @Inject constructor(
    private val appResources: AppResources,
    private val dataRepository: DataRepository
) : BaseViewModel(dataRepository) {
    private val _screenState = MutableStateFlow(getUiInitialState())
    val screenState = _screenState.asStateFlow()
    private val _progressState = MutableStateFlow<UIState>(UIState.Success)
    val progressState = _progressState.asStateFlow()

    fun getUiInitialState(): AppHomeScreenUIStates {
        return AppHomeScreenUIStates(
            employeeDirectoryEmptyText = appResources.getString(R.string.employee_directory_empty_text),
            buttonText = appResources.getString(R.string.button_text),
            placeHolder = R.drawable.place_holder,
            buttonLabel = appResources.getString(R.string.button_label),
            progressLoadingText = appResources.getString(R.string.progress_loading_text)
        )
    }

    fun onAppHomeScreenEvent(events: AppHomeScreenUIEvents) {
        when (events) {
            is AppHomeScreenUIEvents.GetInitialData -> {
                _progressState.update {
                    UIState.Loading
                }
                getApiData()
            }

            is AppHomeScreenUIEvents.RefreshApiCall -> {
                _progressState.update {
                    UIState.Loading
                }
                _screenState.update {
                    it.copy(
                        employeeDirectoryList = mutableListOf()
                    )
                }
                getApiData()
            }
        }
    }

    private fun getApiData() {
        viewModelScope.launch {
            when (val response = dataRepository.getApplicationApiService().getEmployeeList()) {
                is MainResponse.Success -> {
                    _screenState.update {
                        it.copy(
                            employeeDirectoryList = sortEmployeeByTeam(response.body)
                        )
                    }
                    _progressState.update { UIState.Success }
                    Log.d("Employee_list", "getApiData: ${response.body}")

                }

                is MainResponse.ApiError -> {
                    getApiDataOfEmployeeMalFormed()
                }

                is MainResponse.NetworkError -> {
                    _screenState.update {
                        it.copy(
                            isEmployeeDirectoryNetworkError = mutableStateOf(true),
                            employeeDirectoryEmptyText = appResources.getString(R.string.network_error_text)
                        )
                    }
                    _progressState.update { UIState.Success }
                }

                is MainResponse.UnknownError -> {
                    _screenState.update {
                        it.copy(
                            isEmployeeDirectoryEmptyUnknownError = mutableStateOf(true),
                            employeeDirectoryEmptyText = appResources.getString(R.string.unknown_error_text)
                        )
                    }
                    _progressState.update { UIState.Success }
                }

            }
        }

    }

    private suspend fun getApiDataOfEmployeeMalFormed() {
        val responseEmployeeMalFormed =
            dataRepository.getApplicationApiService().getEmployeeMalFormedList()
        when (responseEmployeeMalFormed) {
            is MainResponse.Success -> {
                _screenState.update {
                    it.copy(
                        employeeDirectoryList = sortEmployeeMalFormedByTeam(
                            responseEmployeeMalFormed.body
                        )
                    )
                }
                _progressState.update { UIState.Success }
                Log.d(
                    "Employee_mal_formed_list",
                    "getApiData: ${responseEmployeeMalFormed.body}"
                )
            }

            is MainResponse.ApiError -> {
                getApiDataOfEmployeeEmpty()
            }

            is MainResponse.NetworkError -> {
                _screenState.update {
                    it.copy(
                        isEmployeeDirectoryNetworkError = mutableStateOf(true),
                        employeeDirectoryEmptyText = appResources.getString(R.string.network_error_text)
                    )
                }
                _progressState.update { UIState.Success }
            }

            is MainResponse.UnknownError -> {
                _screenState.update {
                    it.copy(
                        isEmployeeDirectoryEmptyUnknownError = mutableStateOf(true),
                        employeeDirectoryEmptyText = appResources.getString(R.string.unknown_error_text)
                    )
                }
                _progressState.update { UIState.Success }
            }
        }
    }

    private suspend fun getApiDataOfEmployeeEmpty() {
        val responseEmployeeEmpty =
            dataRepository.getApplicationApiService().getEmployeeEmptyList()
        when (responseEmployeeEmpty) {
            is MainResponse.Success -> {
                _screenState.update {
                    it.copy(
                        isEmployeeDirectoryEmpty = mutableStateOf(true)
                    )
                }
                _progressState.update { UIState.Success }
                Log.d(
                    "Employee_Empty_List",
                    "getApiData: ${responseEmployeeEmpty.body}"
                )
            }

            is MainResponse.ApiError -> {
                _screenState.update {
                    it.copy(
                        isEmployeeDirectoryEmpty = mutableStateOf(true)
                    )
                }
                _progressState.update { UIState.Success }
            }

            is MainResponse.NetworkError -> {
                _screenState.update {
                    it.copy(
                        isEmployeeDirectoryNetworkError = mutableStateOf(true),
                        employeeDirectoryEmptyText = appResources.getString(R.string.network_error_text)
                    )
                }
                _progressState.update { UIState.Success }
            }

            is MainResponse.UnknownError -> {
                _screenState.update {
                    it.copy(
                        isEmployeeDirectoryEmptyUnknownError = mutableStateOf(
                            true
                        ),
                        employeeDirectoryEmptyText = appResources.getString(R.string.unknown_error_text)
                    )
                }
                _progressState.update { UIState.Success }
            }
        }
    }

    private fun sortEmployeeByTeam(employee: EmployeeResponse): MutableList<Employee> {
        val sortedEmployeeByTeam = employee.employees.sortedBy { it.team }
        return sortedEmployeeByTeam.toMutableList()
    }

    private fun sortEmployeeMalFormedByTeam(employee: EmployeeMalFormedResponse): MutableList<Employee> {
        val sortedEmployeeByTeam = employee.employees.sortedBy { it.team }
        return sortedEmployeeByTeam.toMutableList()
    }

}