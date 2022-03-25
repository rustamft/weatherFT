package com.rustamft.weatherft.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.ramcosta.composedestinations.DestinationsNavHost
import com.rustamft.weatherft.ui.screens.NavGraphs
import com.rustamft.weatherft.ui.theme.WeatherFTTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherFTTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
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
