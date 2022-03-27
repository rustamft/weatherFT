package com.rustamft.weatherft.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.datastore.dataStore
import com.ramcosta.composedestinations.DestinationsNavHost
import com.rustamft.weatherft.database.datastore.WeatherPrefsSerializer
import com.rustamft.weatherft.ui.screens.NavGraphs
import com.rustamft.weatherft.ui.theme.WeatherFTTheme
import com.rustamft.weatherft.util.WEATHER_PREFS
import dagger.hilt.android.AndroidEntryPoint

val Context.dataStore by dataStore(WEATHER_PREFS, WeatherPrefsSerializer)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherFTTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}
