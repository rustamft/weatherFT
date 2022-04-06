package com.rustamft.weatherft.ui.screens.forecast

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rustamft.weatherft.util.ROUTE_FORECAST

@Destination(route = ROUTE_FORECAST)
@Composable
fun ForecastScreen(
    navigator: DestinationsNavigator,
    viewModel: ForecastViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Forecast here")
    }
    // TODO
}
