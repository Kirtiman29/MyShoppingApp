package com.example.myshoppingapp.presentation

import android.R
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myshoppingapp.common.State
import com.example.myshoppingapp.domain.models.userData
import com.example.myshoppingapp.presentation.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import perfetto.protos.UiState


@Composable
fun ProfileScreen(
    viewModel: MyViewModel = hiltViewModel(),
    navController: NavController
) {

    val getUserState = viewModel.getUserDataState.collectAsState().value
    val updateUserDataState = viewModel.updateUserDataState.collectAsState()

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
            getUserState.error.isNotEmpty() -> Text(
                "Error: ${getUserState.error}",
                color = Color.Red,
                modifier = Modifier.padding(20.dp)
            )

            getUserState.data.isNotEmpty() -> {
                ProfileContent(userData = getUserState.data.first(),
                    firebaseAuth = FirebaseAuth.getInstance(),
                    navController = navController)
            }
        }


    }

}
@Composable
fun ProfileContent(
    userData: userData,
    viewModel: MyViewModel = hiltViewModel(),
    firebaseAuth: FirebaseAuth,
    navController: NavController
) {
    val updateUserDataState = viewModel.updateUserDataState.collectAsState()
    val uploadImageState = viewModel.uploadUserImageState.collectAsState()

    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var userImage by rememberSaveable { mutableStateOf("") }
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var isEditable by rememberSaveable { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(userData) {
        firstName = userData.firstName
        lastName = userData.lastName
        email = userData.email
        password = userData.password
        confirmPassword = userData.confirmPassword
        phoneNumber = userData.phoneNumber
        address = userData.address
        userImage = userData.userImage
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {

            viewModel.uploadUserImage(it)
            imageUri = it
        }
    }

    if (uploadImageState.value.data != null){
        userImage = uploadImageState.value.data.toString()
        viewModel.updateUserData(
            userData.copy(userImage = userImage)
        )
    }else if (uploadImageState.value.error !=null){
        Toast.makeText(context, uploadImageState.value.error, Toast.LENGTH_SHORT).show()
    } else if (uploadImageState.value.isLoading){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

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
            if (!isEditable) {
                if (userImage.isNotEmpty()) {
                    AsyncImage(
                        model = userImage,
                        contentDescription = "Profile Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Icon",
                        tint = Color.White,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = "Add Photo",
                    tint = Color.White,
                    modifier = Modifier
                        .size(50.dp)
                        .offset(x = 70.dp, y = 50.dp)
                        .clickable {
                            imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }
                )
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        Row {
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                readOnly = !isEditable,
                enabled = true,
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
                value = lastName,
                onValueChange = { lastName = it },
                readOnly = !isEditable,
                enabled = true,
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
            value = email,
            onValueChange = { email = it },
            readOnly = !isEditable,
            enabled = true,
            label = { Text("Email") },
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
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            readOnly = !isEditable,
            enabled = true,
            label = { Text("Phone Number") },
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
            value = address,
            onValueChange = { address = it },
            readOnly = !isEditable,
            enabled = true,
            label = { Text("Address") },
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

        if (showDialog) {
            CustomLogoutDialog(
                onDismissRequest = { showDialog = false },
                onLogout = {
                    firebaseAuth.signOut()
                    navController.navigate(Routes.LoginScreen)

                     },
                userData = userData,
                firebaseAuth = firebaseAuth
            )
        }

        Button(
            onClick = {
                showDialog = true
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
            Text("Log Out", fontSize = 15.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (isEditable) {
            Button(
                onClick = {
                    isEditable = false
                    val updateUserData = userData.copy(
                        firstName = firstName,
                        lastName = lastName,
                        email = email,
                        password = password,
                        confirmPassword = confirmPassword,
                        phoneNumber = phoneNumber,
                        address = address,
                        userImage = userImage
                    )
                    viewModel.updateUserData(updateUserData)
                },
                enabled = !updateUserDataState.value.isLoading,
                modifier = Modifier
                    .width(317.dp)
                    .height(47.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                border = BorderStroke(1.dp, Color(0xFFF68B8B))
            ) {
                if (updateUserDataState.value.isLoading) {
                    CircularProgressIndicator(
                        color = Color(0xFFF68B8B),
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text("Save Profile", fontSize = 15.sp)
                }
            }
        } else {
            Button(
                onClick = { isEditable = true },
                enabled = !updateUserDataState.value.isLoading,
                modifier = Modifier
                    .width(317.dp)
                    .height(47.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                border = BorderStroke(1.dp, Color(0xFFF68B8B))
            ) {
                Text("Edit Profile", fontSize = 15.sp)
            }
        }
    }
}



@Composable
fun CustomLogoutDialog(
    onDismissRequest: () -> Unit,
    onLogout: () -> Unit,
    userData: userData,
    firebaseAuth: FirebaseAuth
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .width(255.dp)
                .height(257.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = userData.userImage,
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("LOG OUT", color = Color(0xFFF68B8B), fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Do you Really\nWant To Logout", color = Color.Black, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFF68B8B)),
                    modifier = Modifier.width(100.dp)
                        .height(40.dp)
                        .clip(RectangleShape)
                ) {
                    Text("Cancel", color = Color(0xFFF68B8B))
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = onLogout,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF68B8B)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Log Out", color = Color.White)
                }
            }
        }
    }
}

