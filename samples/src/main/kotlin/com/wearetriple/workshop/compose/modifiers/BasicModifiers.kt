package com.wearetriple.workshop.compose.modifiers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ModifiersShowcase() {
    Box(
        modifier = Modifier
            .background(Color.Blue)
            .padding(all = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .rotate(degrees = 45f)
                .size(size = 50.dp)
                .background(
                    color = Color.Red,
                    shape = RoundedCornerShape(size = 10.dp)
                )
        )
    }
}