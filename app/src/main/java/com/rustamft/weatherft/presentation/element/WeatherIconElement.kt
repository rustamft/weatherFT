package com.rustamft.weatherft.presentation.element

import android.content.res.Resources
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.rustamft.weatherft.R
import com.rustamft.weatherft.domain.util.TAG_WEATHER_ICON
import com.rustamft.weatherft.presentation.theme.AppTheme

// Icons from https://github.com/manifestinteractive/weather-underground-icons

@Composable
fun WeatherIconElement(
    iconCode: String,
    iconDescription: String,
    iconSize: Dp
) {

    val context = LocalContext.current
    val iconColor = if (AppTheme.darkTheme) {
        "white"
    } else {
        "black"
    }
    val drawableId = try {
        context.resources.getIdentifier(
            "weather_${iconColor}_$iconCode",
            "drawable",
            context.packageName
        )
    } catch (e: Resources.NotFoundException) {
        Log.e(TAG_WEATHER_ICON, e.message.toString())
        0
    }

    if (drawableId == 0) {
        Text(text = stringResource(id = R.string.something_went_wrong))
    } else {
        Image(
            modifier = Modifier.size(iconSize),
            painter = painterResource(id = drawableId),
            contentDescription = iconDescription,
            contentScale = ContentScale.FillBounds
        )
    }
}
