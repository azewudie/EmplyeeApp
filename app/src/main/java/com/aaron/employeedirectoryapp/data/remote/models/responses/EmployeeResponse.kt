package com.aaron.employeedirectoryapp.data.remote.models.responses

import com.google.gson.annotations.SerializedName

data class EmployeeResponse(
    @field:SerializedName("employees")
    val employees: List<Employee>
)

data class Employee(
    @field:SerializedName("biography")
    val biography: String,
    @field:SerializedName("email_address")
    val emailAddress: String,
    @field:SerializedName("employee_type")
    val employeeType: String,
    @field:SerializedName("full_name")
    val fullName: String,
    @field:SerializedName("phone_number")
    val phoneNumber: String,
    @field:SerializedName("photo_url_large")
    val photoUrlLarge: String,
    @field:SerializedName("photo_url_small")
    val photoUrlSmall: String,
    @field:SerializedName("team")
    val team: String,
    @field:SerializedName("uuid")
    val uuid: String
)