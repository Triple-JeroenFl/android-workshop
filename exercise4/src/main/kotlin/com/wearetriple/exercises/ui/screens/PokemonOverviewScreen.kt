package com.wearetriple.exercises.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.wearetriple.exercises.ui.components.Error
import com.wearetriple.exercises.ui.components.PokemonOverviewList
import com.wearetriple.exercises.ui.theme.Dimensions
import com.wearetriple.exercises.ui.viewmodel.PokeDexViewModel

@Composable
fun PokemonOverviewScreen(
    viewModel: PokeDexViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        when (val currentState = state.value) {
            is PokeDexViewModel.State.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimensions.Paddings.large)
                ) {
                    Error(message = currentState.exception)
                }
            }
            PokeDexViewModel.State.Loading -> {
                CircularProgressIndicator()
            }
            is PokeDexViewModel.State.Success -> {
                PokemonOverviewList(
                    display = currentState.display,
                    isLoadingMore = currentState.isLoadingMore,
                    onFetchMoreData = {
                        viewModel.fetchMorePokemons()
                    },
                    onFavorite = {
                        viewModel.storeFavorite(it)
                    },
                    onUnFavorite = {
                        viewModel.removeFavorite(it)
                    }
                )
            }
            PokeDexViewModel.State.Initial -> { /** we want no UI representation at this point */ }
        }
    }
}