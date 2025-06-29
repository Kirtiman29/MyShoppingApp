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
    object  CheckoutScreen


    @Serializable
    object PaymentScreen

    @Serializable
    object EachProductDetailScreen

    @Serializable

    object AllCategoryScreen

    @Serializable
    object EachCategoryItemScreen


}