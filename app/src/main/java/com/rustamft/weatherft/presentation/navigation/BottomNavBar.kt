package com.rustamft.weatherft.presentation.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rustamft.weatherft.presentation.theme.DIMEN_SMALL

@Composable
fun BottomNavBar(
    navController: NavHostController,
    items: List<BottomNavItem>,
    hideForRoutes: List<String> = emptyList()
) {

    val backStackEntry = navController.currentBackStackEntryAsState()

    if (!hideForRoutes.contains(backStackEntry.value?.destination?.route)) {
        BottomNavigation(
            modifier = Modifier.fillMaxWidth(),
            elevation = DIMEN_SMALL,
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.primary
        ) {
            items.forEach { item ->
                val selected = item.route == backStackEntry.value?.destination?.route
                BottomNavigationItem(
                    selected = selected,
                    onClick = {
                        if (!selected) {
                            navController.navigate(item.route)
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.name
                        )
                    }
                )
            }
        }
    }
}
