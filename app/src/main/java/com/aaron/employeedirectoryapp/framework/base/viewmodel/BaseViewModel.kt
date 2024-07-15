package com.aaron.employeedirectoryapp.framework.base.viewmodel

import androidx.lifecycle.ViewModel
import com.aaron.employeedirectoryapp.data.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
    private val dataRepository: DataRepository
): ViewModel(){
    fun getDataRepository():DataRepository{
        return dataRepository
    }
}