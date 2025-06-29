package com.example.myshoppingapp.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.myshoppingapp.presentation.HomeScreen
import com.example.myshoppingapp.presentation.LoginScreen
import com.example.myshoppingapp.presentation.ProfileScreen
import com.example.myshoppingapp.presentation.SignUpScreen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Composable
fun App(
    firebaseAuth: FirebaseAuth
) {
    val navController = rememberNavController()
    var startScreen = if (firebaseAuth.currentUser == null) {
        SubNavigation.LoginSignUpScreen
    } else {
        SubNavigation.MainHomeScreen
    }

    val bottomNavItems = listOf(
        BottomNavItems(
            icon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        BottomNavItems(
            icon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Filled.Favorite
        ),
        BottomNavItems(
            icon = Icons.Filled.ShoppingCart,
            unselectedIcon = Icons.Filled.ShoppingCart
        ),
        BottomNavItems(
            icon = Icons.Filled.Person,
            unselectedIcon = Icons.Filled.Person
        )
    )

    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    // 🔥 Get current route to decide if we want bottom bar
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route


    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White, // solid background
                tonalElevation = 8.dp          // subtle shadow
            ) {
                bottomNavItems.forEachIndexed { index, bottomNavItem ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            when (index) {
                                0 -> navController.navigate(Routes.HomeScreen)
                                1 -> navController.navigate(Routes.WishListScreen)
                                2 -> navController.navigate(Routes.CartScreen)
                                3 -> navController.navigate(Routes.ProfileScreen)
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (selectedItemIndex == index)
                                    bottomNavItem.icon
                                else
                                    bottomNavItem.unselectedIcon,
                                contentDescription = null,
                                modifier = Modifier.size(30.dp) // bigger icon size
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startScreen,
            modifier = Modifier.padding(innerPadding)
        ) {
            navigation<SubNavigation.LoginSignUpScreen>(startDestination = Routes.LoginScreen) {
                composable<Routes.LoginScreen> {
                    LoginScreen(navController = navController)
                }
                composable<Routes.SignUpScreen> {
                    SignUpScreen(navController = navController)
                }
            }

            navigation<SubNavigation.MainHomeScreen>(startDestination = Routes.HomeScreen) {
                composable<Routes.HomeScreen> {
                    HomeScreen(navController = navController)
                }
                composable<Routes.ProfileScreen> {
                    ProfileScreen(navController = navController)
                }
            }
        }
    }

}


data class BottomNavItems(

    val icon: ImageVector,
    val unselectedIcon: ImageVector
)