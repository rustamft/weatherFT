package com.rustamft.weatherft.ui.elements

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable

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
        shape = CircleShape
    )
}
