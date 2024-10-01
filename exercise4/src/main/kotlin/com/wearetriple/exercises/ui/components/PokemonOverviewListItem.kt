package com.wearetriple.exercises.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.wearetriple.exercises.ui.model.display.PokemonOverviewItemDisplay

@Composable
fun PokemonOverviewListItem(
    modifier: Modifier = Modifier,
    display: PokemonOverviewItemDisplay,
    onFavorite: (String) -> Unit,
    onUnFavorite: (String) -> Unit
) {
    val isFavorited = rememberSaveable { mutableStateOf(display.isFavorite) }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(148.dp)
            .clickable {
                if (isFavorited.value) {
                    onUnFavorite(display.id)
                } else {
                    onFavorite(display.id)
                }
                isFavorited.value = !isFavorited.value
            }
        ,
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            contentDescription = "Pokemon image",
            modifier = Modifier.fillMaxSize(),
            model = display.imageUrl
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            NameChip(
                name = display.name,
                isFavorited = isFavorited.value
            )
        }
    }
}

@Composable
private fun NameChip(
    modifier: Modifier = Modifier,
    isFavorited: Boolean,
    name: String
) {
    Box(
        modifier = modifier
            .background(
                color = Color.Black,
                shape = RoundedCornerShape(64.dp)
            )
            .border(
                width = if (isFavorited) 4.dp else 0.dp,
                color = if (isFavorited) Color.Yellow else Color.Unspecified,
                shape = RoundedCornerShape(64.dp)
            )
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewNameChip() {
    NameChip(
        name = "Balbasaur",
        isFavorited = false
    )
}

@PreviewLightDark
@Composable
private fun PreviewFavoritedNameChip() {
    NameChip(
        name = "Balbasaur",
        isFavorited = true
    )
}

