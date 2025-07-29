package com.example.myshoppingapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myshoppingapp.domain.models.CartItem
import com.example.myshoppingapp.domain.models.CheckOutDataModels
import com.example.myshoppingapp.presentation.navigation.Routes
import com.google.firebase.auth.FirebaseAuth


@Composable
fun AddToCartScreen(
    viewModel: MyViewModel = hiltViewModel(),
    navController: NavController,
) {

    val state = viewModel.getCartItemState.collectAsState()

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

        when {
            state.value.isLoading -> Text("Loading...", modifier = Modifier.padding(20.dp))
            state.value.error.isNotEmpty() -> Text(
                "Error: ${state.value.error}",
                color = Color.Red,
                modifier = Modifier.padding(20.dp)
            )

            state.value.data.isNotEmpty() -> {

                CartContent(
                    navController = navController,
                    cartItem = state.value.data

                )


            }
        }


    }


}


@Composable
fun CartContent(
    navController: NavController,
    cartItem: List<CartItem>
) {

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 21.dp)
    ) {

        Text(
            text = "Shopping Cart",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 20.dp)

        )

        Text(
            text = "< Continue Shopping",
            fontSize = 12.sp,
            color = Color.Gray,
            fontWeight = FontWeight.SemiBold

        )
        Row(
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(
                text = "Items",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray,
            )
            Spacer(modifier = Modifier.weight(2f))

            Text(
                text = "Price",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray,
            )
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "QTY",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray,
            )
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Total",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray,
            )

        }


        LazyColumn(
            modifier = Modifier
                .height(400.dp)
                .padding(top = 10.dp)
        ) {
            items(cartItem) { cartItem ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Card(
                        modifier = Modifier
                            .width(75.dp)
                            .height(105.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        if (cartItem.imageUrl.isNotEmpty()) {
                            AsyncImage(
                                model = cartItem.imageUrl,
                                contentDescription = cartItem.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(8.dp))
                            )
                        } else {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(24.dp)
                            )
                        }

                    }

                    //  Spacer(modifier = Modifier.weight(1f))


                    Column(
                        modifier = Modifier
                            .height(105.dp)
                            .padding(top = 8.dp)
                    ) {
                        Text(
                            text = if (cartItem.name.length>8){
                                cartItem.name.substring(0,8)
                            }else{
                                cartItem.name
                            },
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
//                        Text(
//                            text = "$${cartIt}",
//                            fontSize = 13.sp,
//                            color = Color(0xFF5C5757)
//                        )
                    }

                    Spacer(modifier = Modifier.weight(0.5f))
                    Text(
                        text = "Rs: ${cartItem.price}",
                        fontSize = 13.sp,
                        color = Color(0xFF5C5757)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "${cartItem.quantity}",
                        fontSize = 13.sp,
                        color = Color(0xFF5C5757)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "${cartItem.price * cartItem.quantity}",
                        fontSize = 13.sp,
                        color = Color(0xFF5C5757)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))


            }



        }



        Divider(
            color = Color(0xFF5C5757),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp)

        )

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            Text(
                text = "Sub Total",
                fontSize = 13.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "Rs: ${cartItem.sumOf { it.price * it.quantity }}",
                fontSize = 13.sp,
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold
            )




        }
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val firstProductId = cartItem.firstOrNull()?.productId ?: ""
                navController.navigate(Routes.CheckoutScreen(productId = firstProductId))

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp)
                .height(47.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8C8585),
                contentColor = Color.White
            )

        ) {
            Text(
                text = "Check Out",
                fontSize = 15.sp,
                color = Color.White
            )
        }


    }


}