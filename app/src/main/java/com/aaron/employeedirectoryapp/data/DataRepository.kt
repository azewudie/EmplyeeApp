package com.aaron.employeedirectoryapp.data

import com.aaron.employeedirectoryapp.data.remote.MainDataManager
import com.aaron.employeedirectoryapp.data.remote.service.EmployeeDirectoryAPIs

abstract class DataRepository {
    abstract fun getNetworkDataManager():MainDataManager
    abstract fun getApplicationApiService():EmployeeDirectoryAPIs
}