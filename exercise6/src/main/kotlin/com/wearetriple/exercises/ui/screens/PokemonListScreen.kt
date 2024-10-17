package com.wearetriple.exercises.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.wearetriple.exercises.model.domain.Pokemon
import com.wearetriple.exercises.viewmodel.PokedexViewModel

@Composable
fun PokemonListScreen(
    viewModel: PokedexViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsLazyPagingItems()

    Box(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(state.itemCount) { index ->
                state[index]?.let { pokemon ->
                    PokemonListItem(
                        modifier = Modifier.fillMaxWidth(),
                        pokemon = pokemon,
                        onFavoriteClicked = viewModel::toggleFavorite
                    )
                }
            }
        }
    }
}

@Composable
private fun PokemonListItem(modifier: Modifier = Modifier, pokemon: Pokemon, onFavoriteClicked: (Int, Boolean) -> Unit) {
    Row(
        modifier = modifier
            .height(80.dp)
            .background(MaterialTheme.colorScheme.surfaceDim, RoundedCornerShape(4.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = pokemon.imageUrl,
            contentDescription = pokemon.name,
            modifier = Modifier.fillMaxHeight()
                .aspectRatio(1f)
                .background(color = Color.LightGray, RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp))
        )
        Text(text = pokemon.name, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 20.dp).weight(1f))
        IconButton(
            onClick = { onFavoriteClicked(pokemon.id, !pokemon.isFavorite) }
        ) {
            Icon(
                imageVector = if (pokemon.isFavorite) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                },
                tint = Color.Red,
                contentDescription = null
            )
        }
        Spacer(Modifier.width(12.dp))
    }
}