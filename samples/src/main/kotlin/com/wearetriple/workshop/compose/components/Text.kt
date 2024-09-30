package com.wearetriple.workshop.compose.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun TextShowcase() {
    Text(text = "This is a text.")
}

@Preview(showBackground = true)
@Composable
fun TextShowcaseWithStyle() {
    Text(
        text = "This is a text.",
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        textAlign = TextAlign.Center,
        color = Color.Red,
        fontFamily = FontFamily.Serif
    )
}