package com.vaibhavranga.medicalshopapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.vaibhavranga.medicalshopapp.screen.DashboardScreen
import com.vaibhavranga.medicalshopapp.screen.SignInScreen
import com.vaibhavranga.medicalshopapp.screen.SignUpScreen

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = RootNavGraph.Auth,
        modifier = Modifier
            .fillMaxSize()
    ) {

        navigation<RootNavGraph.Auth>(startDestination = Authentication.SignInScreenRoute) {

            composable<Authentication.SignInScreenRoute> {
                SignInScreen(
                    navController = navController,
                    onSignInSuccess = {
                        navController.navigate(RootNavGraph.Dash)
                    }
                )
            }
            composable<Authentication.SignUpScreenRoute> {
                SignUpScreen(
                    navController = navController
                )
            }
        }
        composable<RootNavGraph.Dash> {
            DashboardScreen(
                navController = navController
            )
        }
    }
}