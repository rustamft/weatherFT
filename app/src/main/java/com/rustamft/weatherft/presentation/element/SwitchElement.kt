package com.rustamft.weatherft.presentation.element

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.rustamft.weatherft.presentation.theme.EggshellPaper
import com.rustamft.weatherft.presentation.theme.SkyBlue

@Composable
fun SwitchElement(
    name: String,
    isChecked: Boolean,
    darkTheme: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    var checkedState by remember { mutableStateOf(isChecked) }
    val colors = if (darkTheme) {
        SwitchDefaults.colors(
            checkedThumbColor = EggshellPaper,
            checkedTrackColor = EggshellPaper,
            checkedTrackAlpha = 0.5f,
            uncheckedThumbColor = Color.Gray,
            uncheckedTrackColor = Color.Gray,
            uncheckedTrackAlpha = 0.5f
        )
    } else {
        SwitchDefaults.colors(
            checkedThumbColor = SkyBlue,
            checkedTrackColor = SkyBlue,
            checkedTrackAlpha = 0.5f,
            uncheckedThumbColor = Color.Gray,
            uncheckedTrackColor = Color.Gray,
            uncheckedTrackAlpha = 0.5f
        )
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = name)
        Switch(
            checked = checkedState,
            onCheckedChange = {
                checkedState = it
                onCheckedChange(checkedState)
            },
            colors = colors
        )
    }
}
