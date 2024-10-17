package com.wearetriple.workshop.animation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wearetriple.workshop.navigation.Home
import kotlinx.serialization.Serializable

@Serializable // Add this annotation for your route classes
data object Home

@Serializable
data object Other

@Composable
fun App() {
    val navController = rememberNavController() // Controller
    NavHost(
        navController = navController,
        enterTransition = { fadeIn() + slideInVertically() }, // On NavHost level
        startDestination = Home
    ) {
        composable<Home>(
            enterTransition = { fadeIn() + slideInHorizontally() }
        ) {
            // Implementation
        }
        composable<Other> {
            // Implementation
        }
    }
}