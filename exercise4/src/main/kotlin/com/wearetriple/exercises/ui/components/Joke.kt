package com.wearetriple.exercises.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.wearetriple.exercises.model.JokeResponse
import com.wearetriple.exercises.ui.theme.Dimensions

@Composable
fun Joke(
    modifier: Modifier = Modifier,
    joke: JokeResponse
) {
    Column(
        modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(Dimensions.roundedCornerSize)
            )
            .fillMaxWidth()
            .padding(Dimensions.Paddings.large),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = joke.setup,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = joke.punchline,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@PreviewLightDark
@Composable
fun Preview() {
    Joke(
        joke = JokeResponse(
            type = "",
            setup = "I bought some shoes from a drug dealer.",
            punchline = "I don't know what he laced them with, but I was tripping all day!",
            id = 0,
        )
    )
}