package com.example.myshoppingapp.presentation

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.NotificationAdd
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage


import com.example.myshoppingapp.domain.models.Category
import com.example.myshoppingapp.domain.models.Product


@Composable
fun HomeScreen(
    viewModel: MyViewModel = hiltViewModel()
) {
    val state = viewModel.getAllCategoryState.collectAsState()

    val productState = viewModel.getAllProductState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllCategory()
        viewModel.getAllProduct()
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 35.dp, start = 25.dp, end = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search",
                        tint = Color(0xFF8C8585)
                    )
                },
                label = {
                    Text(
                        text = "Search",
                        color = Color(0xFF8C8585),
                        fontSize = 20.sp,
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF68B8B),
                    unfocusedBorderColor = Color(0xFFF68B8B)
                ),
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                imageVector = Icons.Outlined.NotificationAdd,
                contentDescription = "notification",
                tint = Color(0xFF8C8585),
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp, top = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Categories",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "See more",
                fontSize = 13.sp,
                color = Color(0xFFF68B8B)
            )
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.value.data) {
                CategoryItem(category = it)


            }
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            AsyncImage(
                model = "https://firebasestorage.googleapis.com/v0/b/myshoppingadminapp1.firebasestorage.app/o/Products%2F1750691831469?alt=media&token=b72f6671-04d7-407d-aa57-d88bccaf78df", // Replace with actual URL
                contentDescription = null,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp, top = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Flash Sale",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "See more",
                fontSize = 13.sp,
                color = Color(0xFFF68B8B)
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth()
                .padding(25.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(productState.value.data) {product ->
                ProductItem(product =  product)
            }


        }


    }

}


@Composable
fun ProductItem(product: Product) {

    Column(
        modifier = Modifier.fillMaxWidth()

    ) {

        Card(
            modifier = Modifier
                .width(100.dp)
                .height(140.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            if (product.imageUri ==  ""){
                CircularProgressIndicator()
            }
            else{
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
        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .width(100.dp)
                .height(116.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFF8C8585)),
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(10.dp)
            ) {
                Text(
                    text = product.name,
                    fontSize = 16.sp,
                    color = Color(0xFF8C8585),

                )
                Text(
                    text = product.category,
                    fontSize = 16.sp,
                    color = Color(0xFF8C8585),

                )
                Text(
                    text = "Rs: ${product.finalprice}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF68B8B),
                )

                Text(
                    text = buildAnnotatedString {
                        append("Rs: ")

                        // Original price with strikethrough
                        pushStyle(
                            SpanStyle(
                                textDecoration = TextDecoration.LineThrough,
                                color = Color.Gray,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        append(product.price)
                        pop()

                        append(" ")

                        // Discount in red
                        pushStyle(
                            SpanStyle(
                                color = Color(0xFFF68B8B),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        append("20% off")
                        pop()
                    }
                )
            }


        }

    }

}


//
//@Composable
//fun AsyncImage(
//    model: String,
//    contentDescription: String,
//    modifier: Modifier,
//    contentScale: ContentScale
//) {
//    TODO("Not yet implemented")
//}

@Composable
fun CategoryItem(category: Category) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .border(1.dp, Color(0xFF8C8585), CircleShape)
        ) {
//            AsyncImage(
//                model = category.imageUri,
//                contentDescription = category.name,
//                modifier = Modifier.size(40.dp),
//                contentScale = ContentScale.Fit
//            )
            Text(text = category.imageUri)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = category.name,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF8C8585),
            fontSize = 12.sp,
        )
    }
}