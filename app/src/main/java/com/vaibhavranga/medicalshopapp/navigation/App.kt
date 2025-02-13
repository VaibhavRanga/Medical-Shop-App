package com.vaibhavranga.medicalshopapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vaibhavranga.medicalshopapp.navigation.navGraphs.authNavGraph
import com.vaibhavranga.medicalshopapp.screen.loading.LoadingScreen
import com.vaibhavranga.medicalshopapp.viewModel.ViewModel
import kotlinx.coroutines.delay

@Composable
fun App(
    viewModel: ViewModel = hiltViewModel()
) {
    val userPreferencesData by viewModel.userDetailsFromPrefs.collectAsStateWithLifecycle()
    var startScreen by remember { mutableStateOf<NavGraph>(NavGraph.Loading) }

    LaunchedEffect(key1 = userPreferencesData) {
        startScreen = if (userPreferencesData?.userId == null) {
            delay(500)
            NavGraph.AuthNavGraph
        } else {
            NavGraph.DashNavGraph
        }
    }

    val rootNavController = rememberNavController()
    NavHost(
        navController = rootNavController,
        startDestination = startScreen,
        modifier = Modifier
            .fillMaxSize()
    ) {
        composable<NavGraph.Loading> {
            LoadingScreen()
        }
        authNavGraph(rootNavController = rootNavController)
        composable<NavGraph.DashNavGraph> {
            DashboardNavigation(
                rootNavController = rootNavController
            )
        }
    }
}