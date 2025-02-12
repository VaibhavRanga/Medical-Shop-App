package com.vaibhavranga.medicalshopapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vaibhavranga.medicalshopapp.navigation.navGraphs.authNavGraph
import com.vaibhavranga.medicalshopapp.screen.dash.DashboardScreen

@Composable
fun App() {
    val rootNavController = rememberNavController()
    NavHost(
        navController = rootNavController,
        startDestination = NavGraph.AuthNavGraph,
        modifier = Modifier
            .fillMaxSize()
    ) {
        authNavGraph(rootNavController = rootNavController)
        composable<NavGraph.DashNavGraph> {
            DashboardScreen(
                rootNavController = rootNavController
            )
        }
    }
}