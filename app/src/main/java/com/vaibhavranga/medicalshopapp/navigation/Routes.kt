package com.vaibhavranga.medicalshopapp.navigation

import kotlinx.serialization.Serializable

sealed class RootNavGraph {
    @Serializable
    data object Auth : RootNavGraph()

    @Serializable
    data object Dash : RootNavGraph()
}

sealed class Authentication {
    @Serializable
    data object SignInScreenRoute : Authentication()

    @Serializable
    data object SignUpScreenRoute : Authentication()
}

sealed class Dashboard {
    @Serializable
    data object StocksScreenRoute : Dashboard()

    @Serializable
    data object AddOrderScreenRoute : Dashboard()

    @Serializable
    data object ProfileScreenRoute : Dashboard()
}