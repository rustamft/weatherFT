package com.rustamft.weatherft.ui.elements

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

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
