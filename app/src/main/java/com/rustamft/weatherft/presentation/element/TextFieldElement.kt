package com.rustamft.weatherft.presentation.element

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TextFieldElement(
    text: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        label = {
            Text(label)
        },
        shape = CircleShape,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}
