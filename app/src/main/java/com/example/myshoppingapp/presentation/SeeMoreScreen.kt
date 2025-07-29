package com.example.myshoppingapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
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
import com.example.myshoppingapp.domain.models.Product

@Composable
fun SeeMoreScreen(viewModel: MyViewModel = hiltViewModel(),navController: NavController) {
    LaunchedEffect(Unit) {
        viewModel.getAllProduct()
    }



    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .size(345.dp)
                .offset(x = 180.dp, y = (-160).dp)
                .clip(CircleShape)
                .background(Color(0xFFF68B8B))
        )

        SeeMoreContent(viewModel = viewModel)
    }
}

@Composable
fun SeeMoreContent(viewModel: MyViewModel = hiltViewModel()) {
    val state = viewModel.getAllProductState.collectAsState()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 21.dp, )
    ) {
        Text(
            text = "See More",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "< See Your Favourite\n                one",
            fontSize = 12.sp,
            color = Color(0xFF5C5757)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Items",
                fontSize = 13.sp,
                color = Color(0xFF5C5757)
            )
            Text(
                text = "Price",
                fontSize = 13.sp,
                modifier = Modifier.padding(end = 200.dp),
                color = Color(0xFF5C5757)
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color(0xFF8C8585)
                )
            },
            label = {
                Text(
                    text = "Search",
                    color = Color(0xFF8C8585),
                    fontSize = 20.sp
                )
            },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFF68B8B),
                unfocusedBorderColor = Color(0xFFF68B8B)
            ),
            modifier = Modifier
                .width(340.dp)
                .height(60.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Divider(
            color = Color(0xFF5C5757),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))



        when {
            state.value.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.value.error.isNotBlank() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.value.error,
                        color = Color.Red,
                        fontSize = 16.sp
                    )
                }
            }

            else -> {
                // Show success content like LazyColumn
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(state.value.data) { product ->
                        ProductCont(product = product)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }

    }
}

@Composable
fun ProductCont(product: Product) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .width(75.dp)
                .height(105.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            if (product.imageUri.isBlank()) {
                CircularProgressIndicator()
            } else {
                AsyncImage(
                    model = product.imageUri,
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }

        Column(
            modifier = Modifier
                .height(105.dp)
                .padding(start = 16.dp, top = 8.dp)
        ) {
            Text(
                text = product.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$${product.price}",
                fontSize = 13.sp,
                color = Color(0xFF5C5757)
            )
        }
    }

    Spacer(modifier = Modifier.height(12.dp))
}
