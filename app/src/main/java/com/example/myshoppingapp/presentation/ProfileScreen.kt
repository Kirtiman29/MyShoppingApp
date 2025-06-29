package com.example.myshoppingapp.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CheckboxDefaults.colors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myshoppingapp.domain.models.userData


@Composable
fun ProfileScreen(
    viewModel: MyViewModel = hiltViewModel(),
    navController: NavController
) {

    val getUserState = viewModel.getUserDataState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.getUserData()
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
            getUserState.isLoading -> Text("Loading...", modifier = Modifier.padding(20.dp))
            getUserState.error.isNotEmpty() -> Text("Error: ${getUserState.error}", color = Color.Red, modifier = Modifier.padding(20.dp))
            getUserState.data.isNotEmpty() -> {
                ProfileContent(userData = getUserState.data.first())
            }
        }


    }

}

@Composable
fun ProfileContent(
    userData: userData
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 38.dp, top = 60.dp)
    ) {

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.Gray)

        ) {

        }
        Spacer(modifier = Modifier.height(26.dp))

        Row {

            OutlinedTextField(
                value = userData.firstName,
                onValueChange = {
                    userData.firstName = it
                },
                readOnly = true,

                modifier = Modifier
                    .width(148.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF68B8B),
                    unfocusedBorderColor = Color(0xFFF68B8B),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,

                    ),
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )


            )

            Spacer(modifier = Modifier.width(15.dp))
            OutlinedTextField(
                value = userData.lastName,
                onValueChange = {
                    userData.lastName = it
                },
                readOnly = true,

                modifier = Modifier
                    .width(148.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF68B8B),
                    unfocusedBorderColor = Color(0xFFF68B8B),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,

                    ),
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )


            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = userData.email,
            onValueChange = {
                userData.email = it
            },
            readOnly = true,

            modifier = Modifier
                .width(317.dp)
                .height(60.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFF68B8B),
                unfocusedBorderColor = Color(0xFFF68B8B),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,



                ),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )


        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            readOnly = true,

            modifier = Modifier
                .width(317.dp)
                .height(60.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFF68B8B),
                unfocusedBorderColor = Color(0xFFF68B8B),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,



                ),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )


        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            readOnly = true,

            modifier = Modifier
                .width(317.dp)
                .height(60.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFF68B8B),
                unfocusedBorderColor = Color(0xFFF68B8B),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,



                ),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )


        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {},
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
                text = "Log Out",
                fontSize = 15.sp,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .width(317.dp)
                .height(47.dp),
            shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(
              containerColor = Color.White,
              contentColor = Color.Black
          ),
            border = BorderStroke(
                width = 1.dp,
                color = Color(0xFFF68B8B)
            )



        ) {
            Text(
                text = "Edit Profile",
                fontSize = 15.sp,
            )
        }


    }
}