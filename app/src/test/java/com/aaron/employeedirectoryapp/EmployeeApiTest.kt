package com.aaron.employeedirectoryapp

import com.aaron.employeedirectoryapp.data.remote.MainResponse
import com.aaron.employeedirectoryapp.data.remote.models.responses.Employee
import com.aaron.employeedirectoryapp.data.remote.models.responses.EmployeeEmptyResponse
import com.aaron.employeedirectoryapp.data.remote.models.responses.EmployeeResponse
import com.aaron.employeedirectoryapp.data.remote.service.EmployeeDirectoryAPIs
import com.aaron.employeedirectoryapp.di.NetworkModule
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class EmployeeApiTest {
    private lateinit var employeeDirectoryAPIs: EmployeeDirectoryAPIs
    private lateinit var employeeApiResponse: EmployeeResponse
    private lateinit var employeeEmptyApiResponse: EmployeeEmptyResponse


    @Before
    fun setUp() =
        runTest {
            employeeApiResponse =
                EmployeeResponse(
                    employees = listOf(
                        Employee(
                            biography = "Hello",
                            emailAddress = "Hello@test.com",
                            fullName = "hello test",
                            phoneNumber = "1,2,3,4",
                            photoUrlLarge = "",
                            photoUrlSmall = "",
                            team = "",
                            uuid = "",
                            employeeType = ""
                        )
                    )
                )
            employeeEmptyApiResponse =
                EmployeeEmptyResponse(
                    employees = emptyList()
                )
            employeeDirectoryAPIs = NetworkModule().provideTokenLevelRetrofit(
                NetworkModule().provideBasicOkHttpClient(),
            ).create(EmployeeDirectoryAPIs::class.java)

        }

    @Test
    fun emptyApiResponse() =
        runTest {
            when (val response = employeeDirectoryAPIs.getEmployeeEmptyList()) {
                is MainResponse.Success -> {
                    assert(true)
                    Truth.assertThat(response.body.employees)
                        .isEqualTo(employeeEmptyApiResponse.employees)
                }

                is MainResponse.ApiError -> {
                    assert(false)

                }

                is MainResponse.NetworkError -> {
                    assert(false)
                }

                is MainResponse.UnknownError -> {
                    assert(false)
                }
            }
        }

    @Test
    fun employeeApiResponse() =
        runTest {
            when (val response = employeeDirectoryAPIs.getEmployeeList()) {
                is MainResponse.Success -> {
                    assert(true)
                    Truth.assertThat(response.body.employees).isNotEmpty()
                    Truth.assertThat(response.body.employees)
                        .isNotEqualTo(employeeApiResponse.employees)
                }

                is MainResponse.ApiError -> {
                    assert(false)

                }

                is MainResponse.NetworkError -> {
                    assert(false)
                }

                is MainResponse.UnknownError -> {
                    assert(false)
                }
            }
        }
    @Test
    fun employeeMalFormedApiResponse() =
        runTest {
            when (val response = employeeDirectoryAPIs.getEmployeeMalFormedList()) {
                is MainResponse.Success -> {
                    assert(true)
                    Truth.assertThat(response.body.employees).isNotEmpty()
                    Truth.assertThat(response.body.employees)
                        .isNotEqualTo(employeeApiResponse.employees)
                }

                is MainResponse.ApiError -> {
                    assert(false)

                }

                is MainResponse.NetworkError -> {
                    assert(false)
                }

                is MainResponse.UnknownError -> {
                    assert(false)
                }
            }
        }

    @After
    fun tearDown() {

    }


}
