package com.rustamft.weatherft.presentation.element

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.rustamft.weatherft.presentation.theme.EggshellPaper
import com.rustamft.weatherft.presentation.theme.SkyBlue

@Composable
fun IconButtonElement(
    onClick: () -> Unit,
    painter: Painter,
    contentDescription: String
) {

    val tint = if (isSystemInDarkTheme()) {
        EggshellPaper
    } else {
        SkyBlue
    }

    IconButton(
        onClick = onClick
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}
