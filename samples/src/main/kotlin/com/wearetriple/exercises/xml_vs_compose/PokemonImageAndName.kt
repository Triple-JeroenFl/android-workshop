package com.wearetriple.exercises.xml_vs_compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.wearetriple.exercises.R

@Preview
@Composable
private fun PokemonImageAndName() {
    Surface {
        Column {
            Image(
                painter = painterResource(R.drawable.bulbasaur),
                contentDescription = "Bulbasaur"
            )
            Text(text = "Bulbasaur")
        }
    }
}
