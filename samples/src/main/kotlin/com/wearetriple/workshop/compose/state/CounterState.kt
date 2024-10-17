package com.wearetriple.workshop.compose.state

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun CounterStateShowcase() {
    var counter by remember { mutableStateOf(0) }

    Column {
        Text(text = "Counter: $counter")
        Button(
            onClick = { counter++ }
        ) {
            Text(text = "Click to increment!")
        }
    }
}