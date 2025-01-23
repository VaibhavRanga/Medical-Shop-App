package com.vaibhavranga.medicalshopapp.navigation

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.vaibhavranga.medicalshopapp.screen.AddOrderScreen
import com.vaibhavranga.medicalshopapp.screen.ProfileScreen
import com.vaibhavranga.medicalshopapp.screen.SignInScreen
import com.vaibhavranga.medicalshopapp.screen.SignUpScreen
import com.vaibhavranga.medicalshopapp.screen.StocksScreen
import com.vaibhavranga.medicalshopapp.viewModel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun App(
    viewModel: ViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    val userDetailsFromPrefs = viewModel.userDetailsFromPrefs.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            viewModel.getUserDetailsFromPrefs()
        }
    }

    val startScreen = remember {
        if (userDetailsFromPrefs.value?.userId == null) {
            Log.d("TAG", "dashboard variable check for null: ${userDetailsFromPrefs.value}")
            SubGraph.Auth
        } else {
            Log.d("TAG", "dashboard variable check for not null: ${userDetailsFromPrefs.value}")
            SubGraph.Dashboard
        }
    }
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

        NavHost(
            navController = navController,
            startDestination = startScreen,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            navigation<SubGraph.Auth>(startDestination = Routes.SignInScreenRoute) {

                composable<Routes.SignInScreenRoute> {
                    SignInScreen(
                        navController = navController,
                        onSignInSuccess = {
                            navController.navigate(SubGraph.Dashboard)
                        }
                    )
                }
                composable<Routes.SignUpScreenRoute> {
                    SignUpScreen(
                        navController = navController
                    )
                }
            }

            navigation<SubGraph.Dashboard>(startDestination = Routes.StocksScreenRoute) {

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
                            navController.navigate(SubGraph.Auth)
                        }
                    )
                }
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