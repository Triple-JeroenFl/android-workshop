package com.wearetriple.workshop.compose.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun LazyColumnShowcase() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(count = 5) { index ->
            Text(text = "Item: $index")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LazyGridShowcase() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2)
    ) {
        items(count = 5) { index ->
            Text(text = "Item: $index")
        }
    }
}