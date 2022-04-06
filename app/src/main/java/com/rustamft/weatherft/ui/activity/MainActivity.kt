package com.rustamft.weatherft.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.rustamft.weatherft.ui.navigation.BottomNavBar
import com.rustamft.weatherft.ui.navigation.BottomNavItem
import com.rustamft.weatherft.ui.screens.NavGraphs
import com.rustamft.weatherft.ui.theme.WeatherFTTheme
import com.rustamft.weatherft.util.ROUTE_FORECAST
import com.rustamft.weatherft.util.ROUTE_WEATHER
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherFTTheme {

                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        BottomNavBar(
                            navController = navController,
                            items = listOf(
                                BottomNavItem(
                                    "Current weather",
                                    ROUTE_WEATHER,
                                    Icons.Default.Home
                                ),
                                BottomNavItem(
                                    "Weather forecast",
                                    ROUTE_FORECAST,
                                    Icons.Default.List
                                )
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            onItemClick = { navController.navigate(it.route) }
                        )
                    }
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root, navController = navController)
                }
            }
        }
    }
}
