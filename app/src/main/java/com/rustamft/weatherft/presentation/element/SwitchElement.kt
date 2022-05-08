package com.rustamft.weatherft.presentation.element

import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
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

@Composable
fun SwitchElement(
    name: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    var checkedState by remember { mutableStateOf(isChecked) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = name)
        Switch(
            checked = checkedState,
            onCheckedChange = {
                checkedState = it
                onCheckedChange(checkedState)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.primary,
                checkedTrackColor = MaterialTheme.colors.primary,
                checkedTrackAlpha = 0.5f,
                uncheckedThumbColor = Color.Gray,
                uncheckedTrackColor = Color.Gray,
                uncheckedTrackAlpha = 0.5f
            )
        )
    }
}
