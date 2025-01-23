package com.vaibhavranga.medicalshopapp.navigation

import kotlinx.serialization.Serializable

sealed class SubGraph {
    @Serializable
    data object Auth : SubGraph()

    @Serializable
    data object Dashboard : SubGraph()
}

sealed class Routes {

    @Serializable
    data object SignInScreenRoute : Routes()

    @Serializable
    data object SignUpScreenRoute : Routes()

    @Serializable
    data object StocksScreenRoute : Routes()

    @Serializable
    data object AddOrderScreenRoute : Routes()

    @Serializable
    data object ProfileScreenRoute : Routes()
}