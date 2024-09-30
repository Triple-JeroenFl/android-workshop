package com.wearetriple.workshop.compose.modifiers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun ModifierOrderShowcase() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .background(Color.Red)
                .padding(10.dp) // Padding after background before size
                .size(50.dp)
        )
        Box(
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp) // Padding before background after size
                .background(Color.Blue)
        )
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Green)
                .padding(10.dp) // Padding after background and after size
        )
    }
}