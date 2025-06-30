package com.example.myshoppingapp.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

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


    when{
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

            ){
                Text(text = state.value.error)
                Log.d("error", state.value.error)
            }


        }
        state.value.data != null -> {
            Toast.makeText(context, "Data Load Successfully", Toast.LENGTH_SHORT).show()

            Text(text = state.value.data!!.name)

        }

    }

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,


    ){

    }

}