package com.example.myshoppingapp.presentation

import android.util.Log
import android.widget.Toast

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myshoppingapp.domain.models.CartItem
import com.example.myshoppingapp.domain.models.Product
import com.example.myshoppingapp.presentation.navigation.Routes
import kotlinx.coroutines.selects.whileSelect

@Composable
fun EachProductDetailScreen(
    viewModel: MyViewModel = hiltViewModel(),
    navController: NavController,
    productID: String,
) {

    val state = viewModel.getProductByIdState.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(key1 = Unit) {
        Log.d("ProductScreen", "Fetching product with ID: $productID")
        viewModel.getProductById(productID)

    }



    when {
        state.value.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center

            ) {
                CircularProgressIndicator()
            }

        }

        state.value.error.isNotBlank() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center

            ) {
                Text(text = state.value.error)
                Log.d("error", state.value.error)
            }


        }

        state.value.data != null -> {
            Toast.makeText(context, "Data Load Successfully", Toast.LENGTH_SHORT).show()
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                LazyColumn {
                    item {
                        EachProductDetailContnet(product = state.value.data!!, navController = navController)
                    }
                }
            }



        }

    }


}

@Composable
fun EachProductDetailContnet(product: Product,navController: NavController
,viewModel: MyViewModel = hiltViewModel()) {

    val cartState = viewModel.addToCardState.collectAsState()
  val  context = LocalContext.current

    var quantity by rememberSaveable { mutableIntStateOf(1) }

    LaunchedEffect(cartState.value) {
        when {
            cartState.value.isLoading -> {
                // Optional: show loading indicator
            }

            cartState.value.error.isNotBlank() -> {
                Log.e("CartError", cartState.value.error)
                Toast.makeText(context, "Error: ${cartState.value.error}", Toast.LENGTH_SHORT)
                    .show()
            }

            cartState.value.data != null -> {
                Toast.makeText(context, cartState.value.data ?: "Added", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            // Background image
            AsyncImage(
                model = product.imageUri,
                contentDescription = product.name,
                modifier = Modifier.fillMaxSize(),
                // contentScale = ContentScale.Crop
            )

            // Smoke gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.White.copy(alpha = 0.9f)
                            ),
                            startY = 300f
                        )
                    )
            )

            // Content on top of gradient
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 30.dp, bottom = 30.dp)
            ) {
                Text(
                    text = product.name,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                StarRating(
                    rating = 4,
                    totalStars = 5,
                    starSize = 25.dp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 18.sp)) {
                            append("Rs: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(product.price)
                        }
                    },
                    color = Color.Black
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Size",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black

                )
                Text(
                    text = "See More",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF68B8B)

                )

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RectangleShape)
                        .border(1.dp, Color(0xFFF68B8B), RectangleShape)

                ) {
                    Text(
                        text = "UK 8",
                        modifier = Modifier.align(Alignment.Center)
                    )

                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RectangleShape)
                        .border(1.dp, Color(0xFFF68B8B), RectangleShape)

                ) {
                    Text(
                        text = "UK 10",
                        modifier = Modifier.align(Alignment.Center)
                    )

                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RectangleShape)
                        .border(1.dp, Color(0xFFF68B8B), RectangleShape)

                ) {
                    Text(
                        text = "UK 12",
                        modifier = Modifier.align(Alignment.Center)
                    )

                }
                Spacer(modifier = Modifier.width(30.dp))


                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RectangleShape)
                        .clickable {
                            quantity++
                        }


                ) {
                    Text(
                        text = "+",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)

                    )

                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RectangleShape)
                        .border(1.dp, Color(0xFFF68B8B), RectangleShape)

                ) {
                    Text(
                        text = "$quantity",
                        modifier = Modifier.align(Alignment.Center)
                    )

                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RectangleShape)
                        .clickable {
                            if (quantity > 1) {
                                quantity--
                            }
                        }


                ) {
                    Text(
                        text = "-",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)

                    )

                }


            }
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Color",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black

            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RectangleShape)
                        .border(1.dp, Color(0xFFF68B8B), RectangleShape)
                        .background(Color(0xFFF68B8B))


                )
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RectangleShape)
                        // .border(1.dp, Color(0xFFF68B8B), RectangleShape)
                        .background(Color(0xFF3DC6AD))


                )
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RectangleShape)
                        //.border(1.dp, Color(0xFFF68B8B), RectangleShape)
                        .background(Color(0xFF27A624B5))


                )
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RectangleShape)
                        //.border(1.dp, Color(0xFFF68B8B), RectangleShape)
                        .background(Color(0xFFFBE417))


                )

            }
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Specification",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = product.category,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8C8585)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = product.description,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8C8585)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    navController.navigate(Routes.CheckoutScreen(productId = product.id))

                },
                modifier = Modifier
                    .width(317.dp)
                    .height(47.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF68B8B),
                    contentColor = Color.White
                )

            ) {
                Text(
                    text = "Buy now",
                    fontSize = 15.sp,
                    color = Color.White
                )
            }
        Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    val cartItem = CartItem(
                        productId = product.id,
                        name = product.name,
                        description = product.description,
                        price = product.finalprice.toDouble(),
                        quantity = quantity,
                        imageUrl = product.imageUri.toString()
                    )
                    viewModel.addToCardData(cartItem)

                },
                modifier = Modifier
                    .width(317.dp)
                    .height(47.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8C8585),
                    contentColor = Color.White
                )

            ) {
                Text(
                    text = "Add to Cart",
                    fontSize = 15.sp,
                    color = Color.White
                )
            }

        Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {},
                modifier = Modifier
                    .width(317.dp)
                    .height(47.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                )

            ) {
                Row {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = Color(0xFFF68B8B)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Add to Watchlist",
                        fontSize = 15.sp,
                        color = Color(0xFFF68B8B)
                    )
                }

            }



        }

    }
}


@Composable
fun StarRating(
    rating: Int = 4,
    totalStars: Int = 5,
    starSize: Dp = 25.dp,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp) // space between stars
    ) {
        for (i in 1..totalStars) {
            if (i <= rating) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.scale(starSize.value / 24f)
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.scale(starSize.value / 24f)
                )
            }
        }
    }
}
