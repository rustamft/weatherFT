package com.rustamft.weatherft.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.rustamft.weatherft.domain.util.ROUTE_LOGIN
import com.rustamft.weatherft.presentation.navigation.TopBar
import com.rustamft.weatherft.presentation.screen.NavGraphs
import com.rustamft.weatherft.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val mainViewModel: MainViewModel = hiltViewModel()
            val appPreferencesState = mainViewModel.appPreferencesState
            val scaffoldState = rememberScaffoldState()
            val navController = rememberNavController()

            AppTheme(darkTheme = appPreferencesState.value.darkTheme) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopBar(
                            navController = navController,
                            shrinkForRoutes = listOf(ROUTE_LOGIN)
                        )
                    }
                ) {
                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        navController = navController,
                        dependenciesContainerBuilder = {
                            dependency(scaffoldState)
                            dependency(appPreferencesState)
                        }
                    )
                }
            }
        }
    }
}
