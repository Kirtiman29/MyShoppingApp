package com.example.myshoppingapp.presentation

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myshoppingapp.domain.models.Product

@Composable
fun CheckOutScreen(
    viewModel: MyViewModel = hiltViewModel(),
    navController: NavController,
    productID: String
) {

    val state = viewModel.getProductByIdState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
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
            }
        }

        state.value.data != null -> {
            Toast.makeText(context, "Data Load Successfully", Toast.LENGTH_SHORT).show()
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn {
                    item {
                        CheckOutContent(product = state.value.data!!)
                    }
                }
            }
        }
    }

}


@Composable
fun CheckOutContent(product: Product) {


    var email by rememberSaveable { mutableStateOf("") }
    var country by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var city by rememberSaveable { mutableStateOf("") }
    var state by rememberSaveable { mutableStateOf("") }
    var pincode by rememberSaveable { mutableStateOf("") }
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }

    var onCheckedChange by rememberSaveable { mutableStateOf(false) }

    var isChecked by rememberSaveable { mutableStateOf(false) }
    var isCOD by rememberSaveable { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp)
    ) {
        Text(
            text = "Shipping",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "< Return to cart",
            color = Color(0xFF5C5757),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
//                .height(190.dp)
                .background(Color.Transparent)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                AsyncImage(
                    model = product.imageUri,
                    contentDescription = "product image",
                    modifier = Modifier
                        .width(55.dp)
                        .height(87.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(10.dp))


                Text(
                    text = product.name,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5C5757)

                )
                Spacer(modifier = Modifier.width(130.dp))

                Text(
                    text = "Rs: ${product.finalprice}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5C5757),

                    )


            }
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp)
                    .height(1.dp)
                    .border(1.dp, Color(0xFF5C5757))
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Sub Total",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF5C5757)
                )
                Text(
                    text = "Rs: ${product.finalprice}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF5C5757)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween


            ) {
                Text(
                    text = "Shipping",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF5C5757)
                )

                Text(
                    text = "Free",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF5C5757)
                )


            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp)
                    .height(1.dp)
                    .border(1.dp, Color(0xFF5C5757))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween


            ) {
                Text(
                    text = "Total",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF5C5757)
                )

                Text(
                    text = "Rs: ${product.finalprice}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF5C5757)
                )


            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp)
                    .height(1.dp)
                    .border(1.dp, Color(0xFF5C5757))
            )


        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween


        ) {
            Text(
                text = "Contact Information",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5C5757)
            )

            Text(
                text = "Already have an account? Login",
                fontSize = 12.sp,
                color = Color(0xFF5C5757)
            )


        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                    text = "Email",
                    modifier = Modifier.padding(),
                    color = Color(0xFF8C8585)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp)
                .height(60.dp),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFF68B8B),
                unfocusedBorderColor = Color(0xFFF68B8B),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,


                )

        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Shipping Address",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5C5757)

        )

        OutlinedTextField(
            value = country,
            onValueChange = { country = it },
            label = {
                Text(
                    text = "Country / Region",
                    modifier = Modifier.padding(),
                    color = Color(0xFF8C8585)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp)
                .height(60.dp),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFF68B8B),
                unfocusedBorderColor = Color(0xFFF68B8B),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,


                )

        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = {
                    Text(
                        text = "First Name",
                        fontSize = 13.sp,
                        modifier = Modifier.padding(horizontal = 20.dp),
                        color = Color(0xFF8C8585)
                    )
                },
                modifier = Modifier
                    .width(163.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF68B8B),
                    unfocusedBorderColor = Color(0xFFF68B8B),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,

                    )


            )

            Spacer(modifier = Modifier.width(15.dp))
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = {
                    Text(
                        text = "Last Name",
                        fontSize = 13.sp,
                        modifier = Modifier.padding(horizontal = 20.dp),
                        color = Color(0xFF8C8585)
                    )
                },
                modifier = Modifier
                    .width(163.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF68B8B),
                    unfocusedBorderColor = Color(0xFFF68B8B),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,

                    )


            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = {
                Text(
                    text = "Address",
                    modifier = Modifier.padding(),
                    color = Color(0xFF8C8585)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp)
                .height(60.dp),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFF68B8B),
                unfocusedBorderColor = Color(0xFFF68B8B),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,


                )

        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = {
                    Text(
                        text = "City",
                        fontSize = 13.sp,
                        modifier = Modifier.padding(horizontal = 20.dp),
                        color = Color(0xFF8C8585)
                    )
                },
                modifier = Modifier
                    .width(163.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF68B8B),
                    unfocusedBorderColor = Color(0xFFF68B8B),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,

                    )


            )

            Spacer(modifier = Modifier.width(15.dp))
            OutlinedTextField(
                value = pincode,
                onValueChange = { pincode = it },
                label = {
                    Text(
                        text = "Postal code",
                        fontSize = 13.sp,
                        modifier = Modifier.padding(horizontal = 20.dp),
                        color = Color(0xFF8C8585)
                    )
                },
                modifier = Modifier
                    .width(163.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF68B8B),
                    unfocusedBorderColor = Color(0xFFF68B8B),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,

                    )


            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = {
                Text(
                    text = "Contact number",
                    modifier = Modifier.padding(),
                    color = Color(0xFF8C8585)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp)
                .height(60.dp),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFF68B8B),
                unfocusedBorderColor = Color(0xFFF68B8B),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,


                )

        )


        Spacer(modifier = Modifier.height(8.dp))

        Row {


            Checkbox(
                checked = onCheckedChange,
                onCheckedChange = {
                    onCheckedChange = it

                },
                modifier = Modifier
                    .size(15.dp)
                    .border(1.dp, Color(0xFFD9D9D9), RoundedCornerShape(5.dp)),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Save this information for next time",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF5C5757)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp)
                .height(80.dp)
                .border(1.dp, Color(0xFFF68B8B), RoundedCornerShape(10.dp))
        ) {
            Column {


                Row(
                    modifier = Modifier.padding(start = 20.dp, top = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Contact",
                        fontSize = 12.sp,
                        color = Color(0xFF5C5757),
                    )
                    Spacer(modifier = Modifier.width(220.dp))
                    Text(
                        text = "Change",
                        fontSize = 12.sp,
                        color = Color(0xFF5C5757),
                    )

                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp)
                        .height(1.dp)
                        .border(1.dp, Color(0xFF5C5757))
                )

                Row(
                    modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Ship to",
                        fontSize = 12.sp,
                        color = Color(0xFF5C5757),
                    )
                    Spacer(modifier = Modifier.width(220.dp))
                    Text(
                        text = "Change",
                        fontSize = 12.sp,
                        color = Color(0xFF5C5757),
                    )

                }

            }

        }
            Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Shipping Method",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5C5757)

        )

    Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp)
                .height(100.dp)
                .border(1.dp, Color(0xFFF68B8B), RoundedCornerShape(10.dp))
        ) {
            Column {


                Row(
                    modifier = Modifier.padding(start = 20.dp, top = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {


                    Text(
                        text = "Standard FREE delivery over Rs:4500",
                        fontSize = 12.sp,
                        color = Color(0xFF5C5757),
                    )
                    Spacer(modifier = Modifier.width(70.dp))
                    Text(
                        text = "Free",
                        fontSize = 12.sp,
                        color = Color(0xFF5C5757),
                    )

                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp)
                        .height(1.dp)
                        .border(1.dp, Color(0xFF5C5757))
                )

                Row(
                    modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Cash on delivery over Rs: 4500 (Free delivery.\n COD processing free only)",
                        fontSize = 12.sp,
                        color = Color(0xFF5C5757),
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "100",
                        fontSize = 12.sp,
                        color = Color(0xFF5C5757),
                    )

                }

            }

        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp)
                .height(47.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8C8585),
                contentColor = Color.White
            )

        ) {
            Text(
                text = "Continue to Shipping",
                fontSize = 15.sp,
                color = Color.White
            )
        }


    }
}

