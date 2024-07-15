package com.aaron.employeedirectoryapp.di


import com.aaron.employeedirectoryapp.BuildConfig
import com.aaron.employeedirectoryapp.data.DataRepository
import com.aaron.employeedirectoryapp.data.MainDataRepository
import com.aaron.employeedirectoryapp.data.remote.MainResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideBasicOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient
            .Builder()

        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideTokenLevelRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(MainResponseAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideEmployeeDirectoryDataRepository(
        retrofit: Retrofit
    ): DataRepository =
        MainDataRepository(
            retrofit
        )
}