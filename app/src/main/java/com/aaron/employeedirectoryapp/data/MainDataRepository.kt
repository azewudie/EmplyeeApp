package com.aaron.employeedirectoryapp.data

import com.aaron.employeedirectoryapp.data.remote.MainDataManager
import com.aaron.employeedirectoryapp.data.remote.service.EmployeeDirectoryAPIs
import retrofit2.Retrofit
import retrofit2.create

class MainDataRepository(
    private var tokenRetrofit: Retrofit,
) : DataRepository() {
    override fun getNetworkDataManager(): MainDataManager =
        MainDataManager(
            tokenRetrofit = tokenRetrofit
        )

    override fun getApplicationApiService(): EmployeeDirectoryAPIs=
        getNetworkDataManager()
            .getTokenClint()
            .create<EmployeeDirectoryAPIs>()

}