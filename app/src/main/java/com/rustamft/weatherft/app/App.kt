package com.rustamft.weatherft.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class App : Application() {

    companion object App {
        val language: String get() = Locale.getDefault().language
    }
}
