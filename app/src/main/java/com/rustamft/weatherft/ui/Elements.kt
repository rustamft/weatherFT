package com.rustamft.weatherft.ui

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun TextButtonElement(
    onClick: () -> Unit,
    text: String,
) {
    TextButton(
        onClick = onClick,
        shape = CircleShape
    ) {
        Text(text = text)
    }
}

@Composable
fun IconButtonElement(
    onClick: () -> Unit,
    painter: Painter,
    contentDescription: String
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription
        )
    }
}

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
