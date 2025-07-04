package com.example.myshoppingapp.presentation.navigation

import kotlinx.serialization.Serializable



sealed class SubNavigation{


    @Serializable
    object  MainHomeScreen : SubNavigation()

    @Serializable
    object LoginSignUpScreen : SubNavigation()
}

sealed class Routes {



    @Serializable
    object LoginScreen

    @Serializable
    object SignUpScreen

    @Serializable
    object HomeScreen


    @Serializable
    object SeeMoreScreen

    @Serializable
    object  ProfileScreen

    @Serializable
    object WishListScreen
    @Serializable
    object  CartScreen

    @Serializable
    data class  CheckoutScreen(
        val productId : String
    )


    @Serializable
    object PaymentScreen

    @Serializable
    data class EachProductDetailScreen(
        val productId : String
    )

    @Serializable

    object AllCategoryScreen

    @Serializable
    object EachCategoryItemScreen


}