package com.rustamft.weatherft.presentation.navigation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rustamft.weatherft.R
import com.rustamft.weatherft.app.App
import com.rustamft.weatherft.domain.util.GITHUB_LINK
import com.rustamft.weatherft.domain.util.ROUTE_LOGIN
import com.rustamft.weatherft.presentation.element.IconButtonElement
import com.rustamft.weatherft.presentation.element.TextButtonElement
import com.rustamft.weatherft.presentation.theme.DIMEN_MEDIUM

@Composable
fun TopBar(
    navController: NavHostController,
    shrinkForRoutes: List<String> = emptyList()
) {

    val backStackEntry = navController.currentBackStackEntryAsState()
    var openDialog by remember { mutableStateOf(false) }
    var openGitHub by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        modifier = Modifier.fillMaxWidth(),
        elevation = DIMEN_MEDIUM,
        actions = {
            if (!shrinkForRoutes.contains(backStackEntry.value?.destination?.route)) {
                IconButtonElement(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = stringResource(R.string.change_city_settings),
                    onClick = {
                        navController.navigate(ROUTE_LOGIN)
                    }
                )
            }
            IconButtonElement(
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = stringResource(R.string.app_info),
                onClick = {
                    openDialog = true
                }
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.primary
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
            backgroundColor = MaterialTheme.colors.background
        )
    }

    if (openGitHub) {
        val uriHandler = LocalUriHandler.current
        val uri = Uri.parse(GITHUB_LINK)
        uriHandler.openUri(uri.toString())
        openGitHub = false
    }
}
