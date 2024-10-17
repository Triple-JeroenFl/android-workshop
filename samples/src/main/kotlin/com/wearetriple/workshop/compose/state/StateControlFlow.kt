package com.wearetriple.workshop.compose.state

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun ControlFlowShowcase() {
    var text by remember { mutableStateOf("") }

    Column {
        // Use conditional control flows to display/hide content
        if (text.isNotEmpty()) {
            Text(text = "Your input: $text")
        }
        BasicTextField(
            value = text,
            onValueChange = { value -> text = value }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ControlFlowLoopShowcase() {
    val names = remember { mutableStateListOf("John", "Jane") }
    var text by remember { mutableStateOf("") }

    Column {
        Text(text = "Names:", fontWeight = FontWeight.Bold)
        // Loops can be used to populate lists
        for (name in names) {
            Text(text = name)
        }
        BasicTextField(
            value = text,
            onValueChange = { value -> text = value }
        )
        Button(
            onClick = {
                names.add(text)
                text = ""
            }
        ) {
            Text("Add name: $text")
        }
    }
}