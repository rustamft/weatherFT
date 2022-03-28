package com.rustamft.weatherft.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

val appLanguage: String get() = Locale.getDefault().language

@HiltAndroidApp
class App : Application()
