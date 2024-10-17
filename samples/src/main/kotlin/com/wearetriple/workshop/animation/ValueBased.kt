package com.wearetriple.workshop.animation

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun ValueBasedAnimationShowcase() {
    var animate by remember { mutableStateOf(true) }

    val alpha: Float by animateFloatAsState(
        targetValue = if (animate) 1f else 0.5f,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing) // Custom tween animation specs
    )
    val rotation: Float by animateFloatAsState(
        targetValue = if (animate) 360f else 0f,
        animationSpec = spring() // Custom spring animation specs
    )
    Box(
        modifier = Modifier.size(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .graphicsLayer(alpha = alpha, rotationZ = rotation) // or separate `.alpha()` and `.rotate()` modifiers
                .background(color = Color.Red)
        )
    }
}