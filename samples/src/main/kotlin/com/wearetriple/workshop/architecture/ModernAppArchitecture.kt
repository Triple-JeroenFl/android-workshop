package com.wearetriple.workshop.architecture

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearetriple.workshop.R
import com.wearetriple.workshop.error.ApiException
import com.wearetriple.workshop.error.PokemonService
import com.wearetriple.workshop.networking.PokemonResponse
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/*
The UI or presentation module. This is an Android module that represents your app presentation.
*/

@Composable
fun PokemonScreen(viewModel: PokemonViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchPokemon()
    }
    // UI Implementation
}

// A specific class for UI states and the necessary display data
sealed class PokemonScreenState {
    data object Loading : PokemonScreenState()
    data class Data(
        @StringRes val title: Int,
        val pokemon: List<Pokemon>,
    ) : PokemonScreenState()
    data object Error : PokemonScreenState()
}

// A specific mapper for mapping your data to whatever your UI needs to display
class PokemonScreenStateMapper @Inject constructor() {

    fun mapData(pokemon: List<Pokemon>) = PokemonScreenState.Data(
        title = R.string.list_title,
        pokemon = pokemon
    )
}

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemonList: GetPokemonList,
    private val mapper: PokemonScreenStateMapper,
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonScreenState>(PokemonScreenState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchPokemon() {
        viewModelScope.launch {
            getPokemonList()
                .onSuccess { pokemon ->
                    _uiState.emit(mapper.mapData(pokemon))
                }
                .onFailure { throwable ->
                    Log.e("PokemonViewModel", "Could not fetch pokemon list", throwable)
                    _uiState.emit(PokemonScreenState.Error)
                }
        }
    }
}

/*
The domain module. This can be a separate pure Kotlin module without Android dependencies.
*/

// This is an entity domain model
data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
)

// This is a use case
class GetPokemonList @Inject constructor(
    private val repository: PokemonRepository // "Inject an interface" but actual implementation will be injected
) {

    /* Notice the `operator` keyword and `invoke` name. It's Kotlin syntactic sugar so you'll be
    * able to call this use case as follows: `val list = GetPokemonList()` */
    suspend operator fun invoke(): Result<List<Pokemon>> {
        /* Business logic can be defined here, e.g. if you need them sorted or grouped in a specific
        way, then you can have a GetPokemonListGroupedByType use case. */
        return repository.getPokemonList()
    }
}

/* In your domain module define an interface of your repository which can be used to inject an
* actual Android specific implementation for compatible dependency inversion. */
interface PokemonRepository {

    suspend fun getPokemonList(): Result<List<Pokemon>>
    suspend fun getPokemon(id: Int): Result<Pokemon>
}

/*
The data module. This is an Android module due to hardware dependencies for storage and networking.
*/

// The implementation in the data layer of your domain layer abstraction.
@Singleton
class PokemonRepositoryImpl @Inject constructor(
    private val service: PokemonService,
    private val mapper: PokemonResponseMapper,
) : PokemonRepository {

    override suspend fun getPokemonList(): Result<List<Pokemon>> {
        return runCatching {
            val response = service.getPokemonList()
            if (response.isSuccessful) {
                response.body()!!.results.map(mapper::mapPokemonResponse)
            } else {
                throw ApiException(code = response.code())
            }
        }
    }

    override suspend fun getPokemon(id: Int): Result<Pokemon> {
        return runCatching {
            val response = service.getPokemon(id)
            if (response.isSuccessful) {
                mapper.mapPokemonResponse(response.body()!!)
            } else {
                throw ApiException(code = response.code())
            }
        }
    }
}

// A specific mapper to map data/network responses to your domain model
class PokemonResponseMapper @Inject constructor() {

    fun mapPokemonResponse(response: PokemonResponse): Pokemon {
        val id = response.url.split("/").dropLast(1).last().toInt()
        val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
        return Pokemon(
            id = id,
            name = response.name,
            imageUrl = imageUrl
        )
    }
}

/* Custom Hilt module to bind your repository impl to the interface. This way when a use case in the
* domain layer needs the repository it can request the interface and receive the actual implementation,
* this is dependency inversion in practice. */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Binds
    fun bindPokemonRepository(pokemonRepositoryImpl: PokemonRepositoryImpl): PokemonRepository = pokemonRepositoryImpl
}

