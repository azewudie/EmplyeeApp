package com.aaron.employeedirectoryapp.data.remote.service

import com.aaron.employeedirectoryapp.data.remote.ApiEndPoints
import com.aaron.employeedirectoryapp.data.remote.MainResponse
import com.aaron.employeedirectoryapp.data.remote.models.responses.EmployeeEmptyResponse
import com.aaron.employeedirectoryapp.data.remote.models.responses.EmployeeMalFormedResponse
import com.aaron.employeedirectoryapp.data.remote.models.responses.EmployeeResponse
import com.aaron.employeedirectoryapp.data.remote.models.responses.ResponseError
import retrofit2.http.GET

interface EmployeeDirectoryAPIs {
    @GET(ApiEndPoints.EMPLOYEE)
    suspend fun getEmployeeList(): MainResponse<EmployeeResponse, ResponseError>

    @GET(ApiEndPoints.EMPLOYEE_MAL_FORMED)
    suspend fun getEmployeeMalFormedList(): MainResponse<EmployeeMalFormedResponse, ResponseError>

    @GET(ApiEndPoints.EMPLOYEE_EMPTY)
    suspend fun getEmployeeEmptyList(): MainResponse<EmployeeEmptyResponse, ResponseError>
}