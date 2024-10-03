package com.wearetriple.exercises.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import com.wearetriple.exercises.ui.components.Joke
import com.wearetriple.exercises.ui.model.JokeFlagsResponse
import com.wearetriple.exercises.ui.model.JokeResponse
import com.wearetriple.exercises.ui.theme.Dimensions
import com.wearetriple.exercises.ui.theme.InhollandExcersisesTheme
import com.wearetriple.exercises.ui.viewmodel.JokeViewModel

@Composable
fun JokeScreen(
    jokeViewModel: JokeViewModel = hiltViewModel()
) {
    val state = jokeViewModel.joke.collectAsState()
    val joke = state.value
    ScreenContent(joke)
}

@Composable
private fun ScreenContent(
    joke: JokeResponse?
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimensions.Paddings.large),
        contentAlignment = Alignment.Center
    ) {
        if (joke == null) {
            CircularProgressIndicator()
        } else {
            Joke(
                joke = joke
            )
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewLoading() {
    InhollandExcersisesTheme {
        ScreenContent(
            joke = null
        )
    }
}

@PreviewLightDark
@Composable
fun PreviewJoke() {
    InhollandExcersisesTheme {
        ScreenContent(
            joke = JokeResponse(
                error = false,
                category = "",
                type = "",
                joke = "Hoe noem je een bij in een chinees restaurant? Een sambalbij!",
                flags = JokeFlagsResponse(
                    nsfw = false,
                    religious = false,
                    political = false,
                    racist = false,
                    sexist = false,
                    explicit = false
                ),
                id = 0,
                safe = true,
                lang = "nl"
            )
        )
    }
}