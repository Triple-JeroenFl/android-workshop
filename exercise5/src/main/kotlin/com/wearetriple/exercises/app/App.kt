package com.wearetriple.exercises.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wearetriple.exercises.ui.screens.PokemonListScreen
import com.wearetriple.exercises.ui.theme.ExerciseTheme

@Composable
fun App() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ExerciseTheme {
            PokemonListScreen()
        }
    }
}