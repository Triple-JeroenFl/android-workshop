package com.wearetriple.workshop.compose.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import com.wearetriple.workshop.compose.theme.Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Theme { // Usually encompasses all of the app content
                App()
            }
        }
    }
}

@Composable
fun App() {
    // Implementation
}