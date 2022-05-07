package com.rustamft.weatherft.app

import android.app.Application
import com.rustamft.weatherft.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class App : Application() {

    companion object App {
        val language: String get() = Locale.getDefault().language
        const val version = BuildConfig.VERSION_NAME
    }
}
