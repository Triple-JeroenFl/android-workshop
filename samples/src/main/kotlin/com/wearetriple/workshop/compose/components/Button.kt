package com.wearetriple.workshop.compose.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun ButtonShowcase() {
    Button(
        onClick = { /* Do something here */ }
    ) {
        Text(
            text = "Click me!"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonShowcaseWithIcon() {
    Button(
        onClick = { /* Do something here */ }
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Checkmark"
        )
        Text(
            text = "Click me!"
        )
    }
}