package com.rustamft.weatherft.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.rustamft.weatherft.ui.navigation.BottomNavBar
import com.rustamft.weatherft.ui.navigation.BottomNavItem
import com.rustamft.weatherft.ui.screens.NavGraphs
import com.rustamft.weatherft.ui.theme.AppTheme
import com.rustamft.weatherft.ui.theme.DIMEN_BIG
import com.rustamft.weatherft.util.ROUTE_FORECAST
import com.rustamft.weatherft.util.ROUTE_WEATHER
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {

                val scaffoldState = rememberScaffoldState()
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
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
                    DestinationsNavHost(
                        modifier = Modifier.padding(bottom = DIMEN_BIG),
                        navGraph = NavGraphs.root,
                        navController = navController,
                        dependenciesContainerBuilder = {
                            dependency(scaffoldState)
                        }
                    )
                }
            }
        }
    }
}
