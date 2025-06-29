package com.example.myshoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.myshoppingapp.presentation.HomeScreen
import com.example.myshoppingapp.presentation.LoginScreen
import com.example.myshoppingapp.presentation.ProfileScreen
import com.example.myshoppingapp.presentation.SeeMoreScreen
import com.example.myshoppingapp.presentation.SignUpScreen
import com.example.myshoppingapp.presentation.navigation.App
import com.example.myshoppingapp.ui.theme.MyShoppingAppTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    val navController = NavHostController(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyShoppingAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   App(firebaseAuth)
//                    SignUpScreen(
//                        navController = navController,
//                    )
                }
            }
        }
    }
}
