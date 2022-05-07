package com.rustamft.weatherft.presentation.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rustamft.weatherft.domain.util.ROUTE_LOGIN

@Composable
fun BottomNavBar(
    navController: NavHostController,
    items: List<BottomNavItem>,
    onItemClick: (BottomNavItem) -> Unit
) {

    val backStackEntry = navController.currentBackStackEntryAsState()

    if (backStackEntry.value?.destination?.route != ROUTE_LOGIN) {
        BottomNavigation(
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach { item ->
                val selected = item.route == backStackEntry.value?.destination?.route
                BottomNavigationItem(
                    selected = selected,
                    onClick = { onItemClick(item) },
                    icon = { Icon(imageVector = item.icon, contentDescription = item.name) }
                )
            }
        }
    }
}
