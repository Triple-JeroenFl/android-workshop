package com.wearetriple.exercises.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.wearetriple.exercises.ui.model.JokeFlagsResponse
import com.wearetriple.exercises.ui.model.JokeResponse

@Composable
fun Joke(
    modifier: Modifier = Modifier,
    joke: JokeResponse
) {
    Box(
        modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = joke.joke,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@PreviewLightDark
@Composable
fun Preview() {
    Joke(
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