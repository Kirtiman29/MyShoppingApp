package com.example.myshoppingapp.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.HeartBroken
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import androidx.navigation.toRoute
import com.example.myshoppingapp.presentation.AddToCartScreen
import com.example.myshoppingapp.presentation.CheckOutScreen
import com.example.myshoppingapp.presentation.EachProductDetailScreen
import com.example.myshoppingapp.presentation.HomeScreen
import com.example.myshoppingapp.presentation.LoginScreen
import com.example.myshoppingapp.presentation.ProfileScreen
import com.example.myshoppingapp.presentation.SeeMoreScreen
import com.example.myshoppingapp.presentation.SignUpScreen
import com.example.myshoppingapp.presentation.WatchListScreen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Composable
fun App(
    firebaseAuth: FirebaseAuth
) {
    val navController = rememberNavController()
    val startScreen = if (firebaseAuth.currentUser == null) {
        SubNavigation.LoginSignUpScreen
    } else {
        SubNavigation.MainHomeScreen
    }

    val bottomNavItems = listOf(
        BottomNavItems(Icons.Filled.Home, Icons.Outlined.Home),
        BottomNavItems(Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder),
        BottomNavItems(Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart),
        BottomNavItems(Icons.Filled.Person, Icons.Outlined.Person)
    )

    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val shouldBottomBar = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(currentRoute) {
        shouldBottomBar.value = when(currentRoute) {
            Routes.LoginScreen::class.qualifiedName,
                Routes.SignUpScreen::class.qualifiedName -> false
            else -> true
        }
    }

//    val bottomBarHiddenRoutes = listOf(
//        Routes.LoginScreen,
//        Routes.SignUpScreen
//    )

   // val isBottomBarVisible = currentRoute != null && currentRoute !in bottomBarHiddenRoutes

    Scaffold(
        bottomBar = {
            if (shouldBottomBar.value) {
                NavigationBar(
                    containerColor = Color.Transparent,
                    tonalElevation = 0.dp
                ) {
                    bottomNavItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                                val route = when (index) {
                                    0 -> Routes.HomeScreen
                                    1 -> Routes.WishListScreen
                                    2 -> Routes.CartScreen
                                    3 -> Routes.ProfileScreen
                                    else -> Routes.HomeScreen
                                }
                                navController.navigate(route) {
                                    // This clears backstack up to MainHomeScreen to prevent stack piling
                                    popUpTo(SubNavigation.MainHomeScreen) { inclusive = false }
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (selectedItemIndex == index) item.icon else item.unselectedIcon,
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        )
                    }
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
                    HomeScreen(navController=navController)
                }
                composable<Routes.ProfileScreen> {
                    ProfileScreen(navController=navController)
                }
                composable<Routes.SeeMoreScreen> {
                    SeeMoreScreen(navController=navController)
                }
                composable<Routes.EachProductDetailScreen> {
                    val data = it.toRoute<Routes.EachProductDetailScreen>()
                    EachProductDetailScreen(navController=navController, productID = data.productId)
                }
            }


            composable<Routes.CheckoutScreen> {
                val data = it.toRoute<Routes.CheckoutScreen>()
                CheckOutScreen(navController=navController, productID = data.productId)
            }

            composable<Routes.CartScreen> {
                val data = it.toRoute<Routes.CartScreen>()
                AddToCartScreen(navController=navController)

            }

            composable<Routes.WishListScreen>{
                WatchListScreen(navController = navController)
            }
        }
    }
}

data class BottomNavItems(

    val icon: ImageVector,
    val unselectedIcon: ImageVector
)