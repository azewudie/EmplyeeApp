package com.aaron.employeedirectoryapp.ui.theme

import androidx.compose.runtime.compositionLocalOf

data class DarkTheme(
    var isDark:Boolean = false,
)
val LocalTheme = compositionLocalOf { DarkTheme() }