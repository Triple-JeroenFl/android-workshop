package com.wearetriple.workshop.compose.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun RowShowcase() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = "Start")
        Text(text = "Center")
        Text(text = "End")
    }
}