package com.vaibhavranga.medicalshopapp.navigation

import kotlinx.serialization.Serializable

sealed class NavGraph {
    @Serializable
    object AuthNavGraph : NavGraph()

    @Serializable
    object DashNavGraph : NavGraph()
}

sealed class Authentication {
    @Serializable
    object SignInScreenRoute : Authentication()

    @Serializable
    object SignUpScreenRoute : Authentication()
}

sealed class Dashboard {
    @Serializable
    object StocksScreenRoute : Dashboard()

    @Serializable
    object AddOrderScreenRoute : Dashboard()

    @Serializable
    object ProfileScreenRoute : Dashboard()
}