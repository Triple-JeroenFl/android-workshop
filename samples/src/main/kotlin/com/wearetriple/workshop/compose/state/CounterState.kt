package com.wearetriple.workshop.compose.state

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun CounterStateShowcase() {
    val counter = remember { mutableStateOf(0) } // remember?! mutableStateOf?!

    Column {
        Text(text = "Counter: ${counter.value}")
        Button(
            onClick = { counter.value++ }
        ) {
            Text(text = "Click to increment!")
        }
    }
}