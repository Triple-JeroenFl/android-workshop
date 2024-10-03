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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import coil.compose.AsyncImage
import com.wearetriple.exercises.ui.model.display.PokemonOverviewItemDisplay
import com.wearetriple.exercises.ui.theme.Dimensions
import com.wearetriple.workshop.R

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
            .height(Dimensions.pokemonListItemHeight)
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
        PokemonImage(
            pokemonImageUrl = display.imageUrl
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
private fun PokemonImage(
    pokemonImageUrl: String
) {
    val pokeBall = painterResource(R.drawable.pokemon_placeholder)
    AsyncImage(
        placeholder = pokeBall,
        error = pokeBall,
        fallback = pokeBall,
        contentDescription = "Pokemon image",
        modifier = Modifier.fillMaxSize(),
        model = pokemonImageUrl
    )
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
                shape = RoundedCornerShape(Dimensions.roundedCornerSize)
            )
            .border(
                width = if (isFavorited) Dimensions.pokomonFavoritedBorderWidth else Dimensions.pokemonUnFavoritedBorderWidth,
                color = if (isFavorited) Color.Yellow else Color.Unspecified,
                shape = RoundedCornerShape(Dimensions.roundedCornerSize)
            )
            .padding(
                vertical = Dimensions.Paddings.small,
                horizontal = Dimensions.Paddings.large
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

