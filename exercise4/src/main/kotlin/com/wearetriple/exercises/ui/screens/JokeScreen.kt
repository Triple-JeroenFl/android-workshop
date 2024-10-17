package com.wearetriple.exercises.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import com.wearetriple.exercises.ui.components.Joke
import com.wearetriple.exercises.model.JokeResponse
import com.wearetriple.exercises.ui.theme.Dimensions
import com.wearetriple.exercises.ui.theme.ExerciseTheme
import com.wearetriple.exercises.viewmodel.JokeViewModel

@Composable
fun JokeScreen(
    jokeViewModel: JokeViewModel = hiltViewModel()
) {
    val joke by jokeViewModel.joke.collectAsState()

    LaunchedEffect(Unit) { // Fetch joke when screen is opened
        jokeViewModel.fetchJoke()
    }

    ScreenContent(
        onNewJoke = { jokeViewModel.fetchJoke() },
        joke = joke
    )
}

@Composable
private fun ScreenContent(
    onNewJoke: () -> Unit,
    joke: JokeResponse?,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimensions.Paddings.large),
        contentAlignment = Alignment.Center
    ) {
        if (joke != null) {
            Joke(
                modifier = Modifier.clickable { onNewJoke() },
                joke = joke
            )
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewJoke() {
    ExerciseTheme {
        ScreenContent(
            onNewJoke = {},
            joke = JokeResponse(
                type = "",
                setup = "I bought some shoes from a drug dealer.",
                punchline = "I don't know what he laced them with, but I was tripping all day!",
                id = 0,
            )
        )
    }
}