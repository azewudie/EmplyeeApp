package com.aaron.employeedirectoryapp.data.remote.models.responses

import com.google.gson.annotations.SerializedName

data class EmployeeEmptyResponse(
    @field:SerializedName("employees")
    val employees: List<Any>
)