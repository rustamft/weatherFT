package com.rustamft.weatherft.presentation.navigation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.rustamft.weatherft.R
import com.rustamft.weatherft.app.App
import com.rustamft.weatherft.domain.util.GITHUB_LINK
import com.rustamft.weatherft.presentation.element.IconButtonElement
import com.rustamft.weatherft.presentation.element.TextButtonElement
import com.rustamft.weatherft.presentation.theme.Black
import com.rustamft.weatherft.presentation.theme.EggshellPaper
import com.rustamft.weatherft.presentation.theme.SkyBlue

@Composable
fun TopBar(darkTheme: Boolean) {

    val backgroundColor = if (darkTheme) {
        Black
    } else {
        Color.White
    }
    val contentColor = if (darkTheme) {
        EggshellPaper
    } else {
        SkyBlue
    }
    var openDialog by remember { mutableStateOf(false) }
    var openGitHub by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        modifier = Modifier.fillMaxWidth(),
        actions = {
            IconButtonElement(
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = stringResource(R.string.app_info),
                darkTheme = darkTheme,
                onClick = {
                    openDialog = true
                }
            )
        },
        backgroundColor = backgroundColor,
        contentColor = contentColor
    )

    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            title = { Text(text = stringResource(id = R.string.app_info)) },
            text = {
                Text(text = "${stringResource(id = R.string.app_info_content)} ${App.version}")
            },
            confirmButton = {
                TextButtonElement(
                    onClick = { openDialog = false },
                    text = stringResource(R.string.action_close)
                )
            },
            dismissButton = {
                TextButtonElement(
                    onClick = { openGitHub = true },
                    text = "GitHub"
                )
            },
            backgroundColor = backgroundColor
        )
    }

    if (openGitHub) {
        val uriHandler = LocalUriHandler.current
        val uri = Uri.parse(GITHUB_LINK)
        uriHandler.openUri(uri.toString())
        openGitHub = false
    }
}
