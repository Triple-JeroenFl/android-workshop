package com.wearetriple.workshop.compose.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun BoxShowcase() {
    Box(
        modifier = Modifier.size(100.dp),
        contentAlignment = Alignment.Center
    ) {
        // Top left blue box
        Box(
            modifier = Modifier.background(Color.Blue).size(50.dp).align(Alignment.TopStart)
        )
        // Bottom right blue box
        Box(
            modifier = Modifier.background(Color.Green).size(50.dp).align(Alignment.BottomEnd)
        )
        // Center red box (by contentAlignment set in parent box), drawn on top because drawn as last
        Box(
            modifier = Modifier.background(Color.Red).size(50.dp)
        )
    }
}