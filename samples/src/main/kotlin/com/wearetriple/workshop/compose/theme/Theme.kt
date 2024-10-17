package com.wearetriple.workshop.compose.theme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Immutable // Annotation that will optimize your code as it indicates it won't change
data class ColorSystem(
    val color: Color,
    val gradient: List<Color>
    /* ... */
)

@Immutable
data class TypographySystem(
    val fontFamily: FontFamily,
    val textStyle: TextStyle
)
/* ... */

@Immutable
data class CustomSystem(
    val value1: Int,
    val value2: String
    /* ... */
)

/*
A CompositionLocal is a scoped data source that can be accessed `upstream` instead of passing data
`downstream` all the way. By using a static CompositionLocal the theme objects can be accessed from
any composable without passing the object all the way down.
*/
val LocalColorSystem = staticCompositionLocalOf {
    ColorSystem(
        color = Color.Unspecified,
        gradient = emptyList()
    )
}

val LocalTypographySystem = staticCompositionLocalOf {
    TypographySystem(
        fontFamily = FontFamily.Default,
        textStyle = TextStyle.Default
    )
}

val LocalCustomSystem = staticCompositionLocalOf {
    CustomSystem(
        value1 = 0,
        value2 = ""
    )
}

// Define the Theme function
@Composable
fun Theme(
    content: @Composable () -> Unit
) {
    // Define theme subsystems
    val colorSystem = ColorSystem(
        color = Color.Green,
        gradient = listOf(Color.White, Color(0xFFD7EFFF))
    )
    val typographySystem = TypographySystem(
        fontFamily = FontFamily.Monospace,
        textStyle = TextStyle(fontSize = 18.sp)
    )
    val customSystem = CustomSystem(
        value1 = 1000,
        value2 = "Custom system"
    )
    // Provide the subsystems for the CompositionLocals
    CompositionLocalProvider(
        LocalColorSystem provides colorSystem,
        LocalTypographySystem provides typographySystem,
        LocalCustomSystem provides customSystem,
        content = content // Everything inside content has access to the theming
    )
}

@Preview(showBackground = true)
@Composable
fun ThemeShowcase() {
    Theme { // content parameter is a lambda
        Text(
            text = "This is a text",
            color = Theme.colors.color,
            style = Theme.typography.textStyle,
            fontFamily = Theme.typography.fontFamily,
        )
    }
}

/*
Use a singleton Theme object to provide easier access to your theme subsystems.
E.g. use `Theme.colors.gradient`.
 */
object Theme {
    val colors: ColorSystem
        @Composable
        get() = LocalColorSystem.current
    val typography: TypographySystem
        @Composable
        get() = LocalTypographySystem.current
    val customSystem: CustomSystem
        @Composable
        get() = LocalCustomSystem.current
    /* ... */
}