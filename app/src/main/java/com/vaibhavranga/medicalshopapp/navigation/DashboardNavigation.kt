package com.vaibhavranga.medicalshopapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vaibhavranga.medicalshopapp.navigation.navGraphs.DashNavGraph
import com.vaibhavranga.medicalshopapp.viewModel.ViewModel

@Composable
fun DashboardNavigation(
    rootNavController: NavHostController,
    dashNavController: NavHostController = rememberNavController(),
    viewModel: ViewModel = hiltViewModel()
) {
    val bottomNavItems = listOf(
        BottomNavItem(
            name = "Stocks",
            route = Dashboard.StocksScreenRoute,
            unSelectedIcon = Icons.Outlined.Home,
            selectedIcon = Icons.Filled.Home,
        ),
        BottomNavItem(
            name = "Order Product",
            route = Dashboard.AddOrderScreenRoute,
            unSelectedIcon = Icons.Outlined.ShoppingCart,
            selectedIcon = Icons.Filled.ShoppingCart
        ),
        BottomNavItem(
            name = "Profile",
            route = Dashboard.ProfileScreenRoute,
            unSelectedIcon = Icons.Outlined.AccountCircle,
            selectedIcon = Icons.Filled.AccountCircle
        )
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by dashNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                bottomNavItems.forEachIndexed { index, navigationItem ->
                    val isSelected = currentDestination?.hierarchy?.any { it.route == navigationItem.route::class.qualifiedName } == true
                    NavigationBarItem(
                        icon = {
                                Icon(
                                    imageVector = if (isSelected) {
                                        navigationItem.selectedIcon
                                    } else {
                                        navigationItem.unSelectedIcon
                                    },
                                    contentDescription = navigationItem.name
                                )
                        },
                        label = { Text(navigationItem.name) },
                        selected = isSelected,
                        onClick = {
                            dashNavController.navigate(navigationItem.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(dashNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        DashNavGraph(
            rootNavController = rootNavController,
            dashNavController = dashNavController,
            viewModel = viewModel,
            innerPadding = innerPadding
        )
    }
}

data class BottomNavItem(
    val name: String,
    val route: Dashboard,
    val unSelectedIcon: ImageVector,
    val selectedIcon: ImageVector
)