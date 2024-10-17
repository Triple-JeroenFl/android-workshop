package com.wearetriple.workshop.compose.resources

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.wearetriple.workshop.R

@Preview(showBackground = true)
@Composable
fun Greeting() {
    Text(
        text = stringResource(R.string.greeting, "world")
    )
}