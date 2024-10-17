package com.wearetriple.exercises.ui

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wearetriple.exercises.ui.screens.first.First
import com.wearetriple.exercises.ui.screens.first.FirstScreen
import com.wearetriple.exercises.ui.screens.second.Second
import com.wearetriple.exercises.ui.screens.second.SecondScreen

@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = First,
        enterTransition = { slideInHorizontally { it } },
        popEnterTransition = { slideInHorizontally { -it } },
        exitTransition = { slideOutHorizontally { -it } },
        popExitTransition = { slideOutHorizontally { it } }
    ) {
        composable<First> {
            FirstScreen(
                onButtonClick = { navController.navigate(Second) }
            )
        }
        composable<Second>(
            popExitTransition = { slideOutVertically { it } }
        ) {
            SecondScreen(
                onButtonClick = { navController.navigateUp() }
            )
        }
    }

}