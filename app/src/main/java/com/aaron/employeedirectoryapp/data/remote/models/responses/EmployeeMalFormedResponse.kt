package com.aaron.employeedirectoryapp.data.remote.models.responses

import com.google.gson.annotations.SerializedName

data class EmployeeMalFormedResponse(
    @field:SerializedName("employees")
    val employees: List<Employee>
)
