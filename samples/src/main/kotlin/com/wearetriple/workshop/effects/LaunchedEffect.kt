package com.wearetriple.workshop.effects

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun Timer() {
    var seconds by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) { // Having `Unit` as key means it will execute once
        while (true) {
            delay(timeMillis = 1000) // Delay 1 second and increment seconds
            seconds++
        }
    }
    Text(text = seconds.toString())
}