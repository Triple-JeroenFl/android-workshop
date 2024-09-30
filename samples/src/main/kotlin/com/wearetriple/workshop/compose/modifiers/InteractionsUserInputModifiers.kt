package com.wearetriple.workshop.compose.modifiers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun ModifierUserInteractionShowcase() {
    Box(
        modifier = Modifier
            .clickable { /* On click action here, automatically adds a ripple effect */ }
            .pointerInput(Unit) { // Many other user input processing options here
                detectTapGestures(
                    onTap = {},
                    onPress = {},
                    onDoubleTap = {},
                    onLongPress = {}
                )
                detectTransformGestures { centroid, pan, zoom, rotation ->  }
                detectDragGestures { change, dragAmount ->  }
            }
    )
}