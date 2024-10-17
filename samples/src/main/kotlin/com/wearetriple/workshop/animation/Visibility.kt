package com.wearetriple.workshop.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun DefaultShowcase() {
    var visible by remember { mutableStateOf(true) }
    // Animated visibility will eventually remove the item from the composition once the animation has finished.
    AnimatedVisibility(visible) {
        Text(text = "Default")
    }
}

@Preview(showBackground = true)
@Composable
fun FadeInShowcase() {
    var visible by remember { mutableStateOf(true) }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Text(text = "Fade in")
    }
}

@Preview(showBackground = true)
@Composable
fun ExpandInShowcase() {
    var visible by remember { mutableStateOf(true) }
    AnimatedVisibility(
        visible = visible,
        enter = expandIn(),
        exit = shrinkOut()
    ) {
        Text(text = "Expand in")
    }
}