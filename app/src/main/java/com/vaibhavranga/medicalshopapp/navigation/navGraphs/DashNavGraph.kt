package com.vaibhavranga.medicalshopapp.navigation.navGraphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.vaibhavranga.medicalshopapp.navigation.Dashboard
import com.vaibhavranga.medicalshopapp.navigation.NavGraph
import com.vaibhavranga.medicalshopapp.screen.dash.AddOrderScreen
import com.vaibhavranga.medicalshopapp.screen.dash.ProfileScreen
import com.vaibhavranga.medicalshopapp.screen.dash.StocksScreen
import com.vaibhavranga.medicalshopapp.viewModel.ViewModel

@Composable
fun DashNavGraph(
    rootNavController: NavHostController,
    dashNavController: NavHostController,
    viewModel: ViewModel,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = dashNavController,
        startDestination = NavGraph.DashNavGraph,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        navigation<NavGraph.DashNavGraph>(startDestination = Dashboard.StocksScreenRoute) {
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
                        rootNavController.navigate(NavGraph.AuthNavGraph)
                    }
                )
            }
        }
    }
}