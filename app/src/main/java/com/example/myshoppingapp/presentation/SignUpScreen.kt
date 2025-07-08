package com.example.myshoppingapp.presentation

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myshoppingapp.R
import com.example.myshoppingapp.domain.models.userData
import com.example.myshoppingapp.presentation.navigation.Routes

@Composable
fun SignUpScreen(
    viewModel: MyViewModel = hiltViewModel(),
    navController: NavController
) {

    val state = viewModel.userRegisterState.collectAsState()
    val context = LocalContext.current

    when{
        state.value.isLoading ->{
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        }
        state.value.userData != null ->{
            Toast.makeText(context, state.value.userData.toString(), Toast.LENGTH_SHORT).show()

            navController.navigate(Routes.HomeScreen)
        }

        state.value.error != null ->{
            Toast.makeText(context, state.value.error.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {


        Box(
            modifier = Modifier
                .size(345.dp)
                .offset(x = 180.dp, y = (-160).dp)
                .clip(CircleShape)
                .background(Color(0xFFF68B8B)) // Light pink-red
        )


        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = (-90).dp, y = 780.dp)
                .clip(CircleShape)
                .background(Color(0xFFF68B8B))
        )

        SignUpContent(navController=navController)



    }

}

@Composable
fun SignUpContent(
    viewModel: MyViewModel = hiltViewModel(),
    navController: NavController
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var userImage by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 124.dp,
                start = 36.dp,
                end = 36.dp
            ),

        ) {

        Text(
            text = "SignUp",
            fontSize = 30.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(30.dp))
        Row {

            OutlinedTextField(
                value = firstName,
                onValueChange = {firstName = it},
                label = {
                    Text(
                        text = "First Name",
                        fontSize = 13.sp,
                        modifier = Modifier.padding(horizontal = 20.dp),
                        color = Color(0xFF8C8585)
                    )
                },
                modifier = Modifier
                    .width(148.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(16.dp),
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
                onValueChange = {lastName = it},
                label = {
                    Text(
                        text = "Last Name",
                        fontSize = 13.sp,
                        modifier = Modifier.padding(horizontal = 20.dp),
                        color = Color(0xFF8C8585)
                    )
                },
                modifier = Modifier
                    .width(148.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF68B8B),
                    unfocusedBorderColor = Color(0xFFF68B8B),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,

                    )


            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = {
                Text(
                    text = "Email",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = Color(0xFF8C8585)
                )
            },
            modifier = Modifier
                .width(317.dp)
                .height(60.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFF68B8B),
                unfocusedBorderColor = Color(0xFFF68B8B),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,



                )

        )
        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = {
                Text(
                    text = "Create Password",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = Color(0xFF8C8585)
                )
            },
            modifier = Modifier
                .width(317.dp)
                .height(60.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFF68B8B),
                unfocusedBorderColor = Color(0xFFF68B8B),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,


                )

        )
        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {confirmPassword = it},
            label = {
                Text(
                    text = "Confirm Password",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = Color(0xFF8C8585)
                )
            },
            modifier = Modifier
                .width(317.dp)
                .height(60.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFF68B8B),
                unfocusedBorderColor = Color(0xFFF68B8B),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,


                )

        )





        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val data = userData(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = password,
                    confirmPassword = confirmPassword,

                )
                viewModel.registerUser(data)


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
                text = "Signup",
                fontSize = 15.sp,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = buildAnnotatedString {
                append("Already have an account? ")
                withStyle(style = SpanStyle(color = Color(0xFFF68B8B))) { // your pink color
                    append("Login ")
                }
            },
            fontSize = 15.sp,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(Routes.LoginScreen)
                },
            textAlign = TextAlign.Center,
            color = Color(0xFF8C8585) // base color for the first part
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "-------------------- OR ---------------------",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {

                },
            textAlign = TextAlign.Center,
            color = Color(0xFF8C8585)
        )

        Spacer(modifier = Modifier.height(20.dp))


        Button(
            onClick = {},
            modifier = Modifier
                .width(317.dp)
                .height(47.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            border = BorderStroke(
                width = 1.dp,
                color = Color(0xFFF68B8B)
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "",
                    tint = Color.Unspecified,
                    // to keep original Facebook blue if it's colored
                )

                //Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Log in with Facebook",
                    fontSize = 15.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,

                    color = Color(0xFF8C8585)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {},
            modifier = Modifier
                .width(317.dp)
                .height(47.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            border = BorderStroke(
                width = 1.dp,
                color = Color(0xFFF68B8B)
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "",
                    tint = Color.Unspecified,
                    // to keep original Facebook blue if it's colored
                )

                // Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Log in with Google ",
                    fontSize = 15.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,

                    color = Color(0xFF8C8585)
                )
            }
        }


    }

}
