package com.wearetriple.workshop.compose.custom_reusable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

// Custom composable
@Composable
fun Greeting(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier, // Pass along a modifier to custom composables so that it can be modified from the caller
        text = "Hello, $text!"
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingList() {
    Column {
        Greeting(text = "Jane", modifier = Modifier.background(color = Color.Red))
        Greeting(text = "John", modifier = Modifier.background(color = Color.Green))
        Greeting(text = "Dave") // Default modifier is used
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier, text: State<String>) { // Should you pass along a State? NO!
    Text(
        modifier = modifier,
        text = "Hello, ${text.value}!"
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingListWithInput() {
    var text by remember { mutableStateOf("") } // `by` keyword removes the State wrapper

    Column {
        BasicTextField(
            value = text,
            onValueChange = { value -> text = value }
        )
        Greeting(text = text, modifier = Modifier.background(color = Color.Red))
        Greeting(text = text, modifier = Modifier.background(color = Color.Green))
        Greeting(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingListLowerUppercaseComponent() {
    var text by remember { mutableStateOf("") }

    Column {
        BasicTextField(
            value = text,
            onValueChange = { value -> text = value }
        )
        Greeting(text = text, modifier = Modifier.background(color = Color.Red))
        Greeting(text = text, modifier = Modifier.background(color = Color.Green))
        Greeting(text = text)
        CustomGreetingUppercaseButton(onUppercase = { text = text.uppercase() })
    }
}

@Composable
fun CustomGreetingUppercaseButton(text: MutableState<String>) { // Don't pass along and modify a state to a composable!!
    Button(
        onClick = {
            text.value = text.value.uppercase()
        }
    ) {
        Text(text = "Uppercase greeting")
    }
}

@Composable
fun CustomGreetingUppercaseButton(onUppercase: () -> Unit) { // Rather use a callback
    Button(
        onClick = onUppercase
    ) {
        Text(text = "Uppercase greeting")
    }
}