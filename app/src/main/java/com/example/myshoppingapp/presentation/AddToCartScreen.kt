package com.example.myshoppingapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myshoppingapp.domain.models.CartItem


@Composable
fun AddToCartScreen(
    viewModel: MyViewModel = hiltViewModel(),
    navController: NavController,
    cartItem: CartItem
) {

    val getCartItem = viewModel.getCartItemState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getCartItem()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            text = cartItem.name
        )
    }



}