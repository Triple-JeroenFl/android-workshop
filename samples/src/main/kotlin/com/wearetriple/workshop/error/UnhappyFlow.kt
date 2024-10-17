package com.wearetriple.workshop.error

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearetriple.workshop.networking.Pokemon
import com.wearetriple.workshop.networking.PokemonListMapper
import com.wearetriple.workshop.networking.PokemonListResponse
import com.wearetriple.workshop.networking.PokemonResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

interface PokemonService {

    // Wrap return value in a `Response` so you can have better control over the result
    @GET("v2/pokemon")
    suspend fun getPokemonList(): Response<PokemonListResponse>

    @GET("v2/pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): Response<PokemonResponse>
}

// A custom exception can be useful for error handling
class ApiException(val code: Int) : RuntimeException()

class Api @Inject constructor(
    private val service: PokemonService
) {

    suspend fun getPokemon(): Result<PokemonListResponse> {
        val response = service.getPokemonList()
        return if (response.isSuccessful) {
            /* Use runCatching to safe-call the !! or some other possible faulty call to wrap it in
            a Result.failure or Result.success */
            runCatching { response.body()!! }
        } else {
            Result.failure(ApiException(code = response.code()))
        }
    }
}

// Define a UI State
sealed class UiState {

    data object Loading : UiState()
    data class Data(val pokemon: List<Pokemon>): UiState()
    data class Error(val throwable: Throwable): UiState()
}

@HiltViewModel
class MyViewModel @Inject constructor(
    private val api: Api,
    private val mapper: PokemonListMapper,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading) // Set default to loading
    val uiState = _uiState.asStateFlow()

    fun fetchPokemon() {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading)
            api.getPokemon()
                .onSuccess { response -> // Happy flow
                    _uiState.update {
                        UiState.Data(pokemon = mapper.mapPokemonList(response))
                    }
                }
                .onFailure { throwable -> // Unhappy flow
                    Log.e("MyViewModel", "Error retrieving Pokémon list", throwable)
                    _uiState.update {
                        UiState.Error(throwable = throwable)
                    }
                }
        }
    }
}


@Composable
private fun Screen(viewModel: MyViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    // The `val state = uiState` is necessary for the compiler smart-casting
    when (val state = uiState) {
        is UiState.Data -> PokemonList(pokemon = state.pokemon)
        is UiState.Error -> ErrorScreen()
        UiState.Loading -> LoadingState()
    }
}

@Composable
private fun PokemonList(modifier: Modifier = Modifier, pokemon: List<Pokemon>) {

}

@Composable
private fun ErrorScreen(modifier: Modifier = Modifier) {

}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {

}