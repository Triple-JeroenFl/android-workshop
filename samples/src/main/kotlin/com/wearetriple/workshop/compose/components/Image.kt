package com.wearetriple.workshop.compose.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.wearetriple.workshop.R

@Preview(showBackground = true)
@Composable
fun ImageShowcase() {
    Image(
        painter = painterResource(R.drawable.bulbasaur),
        contentDescription = "Bulbasaur"
    )
}

@Preview(showBackground = true)
@Composable
fun ImageShowcaseWithExtras() {
    Image(
        painter = painterResource(R.drawable.bulbasaur),
        contentDescription = "Bulbasaur",
        colorFilter = ColorFilter.tint(color = Color.Red, blendMode = BlendMode.Color)
    )
}