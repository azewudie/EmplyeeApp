package com.aaron.employeedirectoryapp.utilities

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppUtilities @Inject constructor(
    @ApplicationContext private val context: Context
){
}