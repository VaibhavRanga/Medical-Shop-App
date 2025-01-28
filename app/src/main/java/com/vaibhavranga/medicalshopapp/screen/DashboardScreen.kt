package com.vaibhavranga.medicalshopapp.screen

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.vaibhavranga.medicalshopapp.navigation.RootNavGraph
import com.vaibhavranga.medicalshopapp.navigation.Routes
import com.vaibhavranga.medicalshopapp.viewModel.ViewModel

@Composable
fun DashboardScreen(
    viewModel: ViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val bottomNavItems = listOf(
        BottomNavItem(
            name = "Stocks",
            route = Routes.StocksScreenRoute,
            unSelectedIcon = Icons.Outlined.Home,
            selectedIcon = Icons.Default.Home,
        ),
        BottomNavItem(
            name = "Order Product",
            route = Routes.AddOrderScreenRoute,
            unSelectedIcon = Icons.Outlined.ShoppingCart,
            selectedIcon = Icons.Default.ShoppingCart
        ),
        BottomNavItem(
            name = "Profile",
            route = Routes.ProfileScreenRoute,
            unSelectedIcon = Icons.Outlined.AccountCircle,
            selectedIcon = Icons.Default.AccountCircle
        )
    )
    var selectedNavItem by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedNavItem == index,
                        onClick = {
                            selectedNavItem = index
                            when (index) {
                                0 -> navController.navigate(item.route)
                                1 -> navController.navigate(item.route)
                                2 -> navController.navigate(item.route)
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (selectedNavItem == index) {
                                    item.selectedIcon
                                } else {
                                    item.unSelectedIcon
                                },
                                contentDescription = item.name
                            )
                        },
                        label = {
                            Text(text = item.name)
                        }
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        NavHost(navController = navController, startDestination =) { }
        navigation<RootNavGraph.Dashboard>(startDestination = Routes.StocksScreenRoute) {

            composable<Routes.StocksScreenRoute> {
                StocksScreen()
            }
            composable<Routes.AddOrderScreenRoute> {
                AddOrderScreen()
            }
            composable<Routes.ProfileScreenRoute> {
                ProfileScreen(
                    onSignOutClick = {
                        viewModel.signOut()
                        navController.navigate(RootNavGraph.Auth)
                    }
                )
            }
        }
    }
}

    data class BottomNavItem(
        val name: String,
        val route: Routes,
        val unSelectedIcon: ImageVector,
        val selectedIcon: ImageVector
    )