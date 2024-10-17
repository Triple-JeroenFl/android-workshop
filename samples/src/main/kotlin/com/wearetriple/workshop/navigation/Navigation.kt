package com.wearetriple.workshop.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable // Add this annotation for your route classes
data object Home

@Serializable
data class Other(val param: String)

@Composable
fun App() {
    val navController = rememberNavController() // Controller
    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            HomeScreen(
                onOtherClicked = { value ->
                    navController.navigate(Other(param = value))
                }
            )
        }
        composable<Other> { backStackEntry ->
            val other: Other = backStackEntry.toRoute()
            OtherScreen(param = other.param)
        }
    }
}

@Composable
fun HomeScreen(onOtherClicked: (String) -> Unit) {
    // Implementation
}

@Composable
fun OtherScreen(param: String) {
    // Implementation
}