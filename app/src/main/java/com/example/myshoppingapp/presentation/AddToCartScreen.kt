package com.example.myshoppingapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myshoppingapp.common.CartItem
import com.example.myshoppingapp.domain.models.CartItem
import com.google.firebase.auth.FirebaseAuth


@Composable
fun AddToCartScreen(
    viewModel: MyViewModel = hiltViewModel(),
    navController: NavController,
) {

    val getCartItem = viewModel.getCartItemState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getCartItem()
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // 🔴 Top-right Circle
        Box(
            modifier = Modifier
                .size(345.dp)
                .offset(x = 180.dp, y = (-160).dp)
                .clip(CircleShape)
                .background(Color(0xFFF68B8B)) // Light pink-red
        )

        // 🔴 Bottom-left Circle
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = (-90).dp, y = 780.dp)
                .clip(CircleShape)
                .background(Color(0xFFF68B8B))
        )
        when {
            getCartItem.value.isLoading -> Text("Loading...", modifier = Modifier.padding(20.dp))
            getCartItem.value.error.isNotEmpty() -> Text(
                "Error: ${getCartItem.value.error}",
                color = Color.Red,
                modifier = Modifier.padding(20.dp)
            )

            getCartItem.value.data.isNotEmpty() -> {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 21.dp)
                ) {
                    items(getCartItem.value.data) { cartItem ->
                        CartContent(
                            viewModel = viewModel,
                            navController = navController,
                            firebaseAuth = FirebaseAuth.getInstance(),
                            cartItem = CartItem()
                        )
                    }
                }


            }
        }


    }


}



@Composable
fun CartContent(
    viewModel: MyViewModel = hiltViewModel(),
    navController: NavController,
    firebaseAuth: FirebaseAuth,
    cartItem: CartItem
) {

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 21.dp,)
    ) {

        Text(
            text = "Shopping Cart",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Text(text = "Name: ${cartItem.name}", fontWeight = FontWeight.Bold)
        Text(text = "Price: ₹${cartItem.price}")
        Text(text = "Qty: ${cartItem.quantity}")
        Text(text = "Description: ${cartItem.description}")

    }

}