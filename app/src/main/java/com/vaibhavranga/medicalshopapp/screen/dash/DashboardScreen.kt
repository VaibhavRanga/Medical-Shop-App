package com.vaibhavranga.medicalshopapp.screen.dash

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vaibhavranga.medicalshopapp.navigation.Dashboard
import com.vaibhavranga.medicalshopapp.navigation.navGraphs.DashNavGraph
import com.vaibhavranga.medicalshopapp.viewModel.ViewModel

@Composable
fun DashboardScreen(
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
    var selectedNavItem by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedNavItem == index,
                        onClick = {
                            selectedNavItem = index
                            dashNavController.navigate(item.route)
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