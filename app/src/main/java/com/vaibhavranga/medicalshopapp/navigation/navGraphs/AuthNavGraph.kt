package com.vaibhavranga.medicalshopapp.navigation.navGraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vaibhavranga.medicalshopapp.navigation.Authentication
import com.vaibhavranga.medicalshopapp.navigation.NavGraph
import com.vaibhavranga.medicalshopapp.screen.auth.SignInScreen
import com.vaibhavranga.medicalshopapp.screen.auth.SignUpScreen

fun NavGraphBuilder.authNavGraph(rootNavController: NavHostController) {

    navigation<NavGraph.AuthNavGraph>(startDestination = Authentication.SignInScreenRoute) {

        composable<Authentication.SignInScreenRoute> {
            SignInScreen(
                onSignInSuccess = {
                    rootNavController.navigate(NavGraph.DashNavGraph)
                },
                onSignUpButtonClick = {
                    rootNavController.navigate(Authentication.SignUpScreenRoute)
                }
            )
        }
        composable<Authentication.SignUpScreenRoute> {
            SignUpScreen(
                pendingApprovalForSignIn = {
                    rootNavController.navigateUp()
                }
            )
        }
    }
}