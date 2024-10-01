package com.wearetriple.exercises.ui.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wearetriple.exercises.ui.screens.PokemonOverviewScreen
import com.wearetriple.exercises.ui.theme.InhollandExcersisesTheme

@Composable
fun App() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        InhollandExcersisesTheme {
            PokemonOverviewScreen()
        }
    }
}