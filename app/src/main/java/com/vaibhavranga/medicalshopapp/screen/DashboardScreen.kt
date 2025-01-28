package com.vaibhavranga.medicalshopapp.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vaibhavranga.medicalshopapp.navigation.Dashboard
import com.vaibhavranga.medicalshopapp.navigation.RootNavGraph
import com.vaibhavranga.medicalshopapp.viewModel.ViewModel

@Composable
fun DashboardScreen(
    navController: NavController,
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
    val navController1 = rememberNavController()
    var selectedNavItem by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedNavItem == index,
                        onClick = {
                            selectedNavItem = index
                            navController1.navigate(item.route)
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

        NavHost(
            navController = navController1,
            startDestination = Dashboard.StocksScreenRoute,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable<Dashboard.StocksScreenRoute> {
                StocksScreen()
            }
            composable<Dashboard.AddOrderScreenRoute> {
                AddOrderScreen()
            }
            composable<Dashboard.ProfileScreenRoute> {
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
    val route: Dashboard,
    val unSelectedIcon: ImageVector,
    val selectedIcon: ImageVector
)