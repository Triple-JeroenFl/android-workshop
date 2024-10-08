package com.wearetriple.exercises.ui.viewmodel

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import com.wearetriple.exercises.ui.mapper.PokemonOverviewDisplayMapper
import com.wearetriple.exercises.ui.mapper.PokemonOverviewMapper
import com.wearetriple.exercises.ui.mapper.ResponseMapper
import com.wearetriple.exercises.ui.model.display.PokemonOverviewDisplay
import com.wearetriple.exercises.ui.model.domain.PokemonOverview
import com.wearetriple.exercises.ui.model.domain.PokemonOverviewItem
import com.wearetriple.exercises.ui.model.response.PokemonOverviewResponse
import com.wearetriple.exercises.ui.service.PokemonService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class PokeDexViewModel @Inject constructor(
    private val pokemonOverviewMapper: PokemonOverviewMapper,
    private val pokemonOverviewDisplayMapper: PokemonOverviewDisplayMapper,
    @ApplicationContext private val context: Context,
    private val moshi: Moshi,
    private val responseMapper: ResponseMapper
) : ViewModel() {

    companion object {
        private const val TAG = "PokeDexViewModel"
        private const val MAX_CACHE_SIZE = 10 * 1024 * 1024L
        private const val NETWORK_TIMEOUT = 30
        private const val FETCH_LIMIT = 20
        private const val BASE_URL = "https://pokeapi.co/api/v2/"
        //<<<<< Legacy >>>>>
        private const val SHARED_PREFERENCES_NAME = "com.wearetriple.exercises.shared.prefs.pokemons_favorited"
        private const val SHARED_PREFERENCES_POKEMONS_FAVORITED_KEY = "pokemons_favorited_key"
        //>>>>><<<<<
        private const val DATA_STORE_NAME = "com.wearetriple.exercises.data.store.preferences.pokemons.favorited"
        private val DATA_STORE_POKEMONS_FAVORITED_KEY = stringPreferencesKey("pokemons_favorited_key")
    }

    private val mutableState = MutableStateFlow<State>(State.Initial)
    val state: StateFlow<State> = mutableState.asStateFlow()

    private val retrofit = buildRetrofit(BASE_URL)
    private val service = retrofit.create(PokemonService::class.java)
    private var currentBatch = 0
    // <<<< legacy >>>>
    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    // >>>>><<<<<
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)
    private val fetchedPokemons: MutableList<PokemonOverviewItem> = mutableListOf()

    init {
        fetchPokemonOverview(
            batch = 0,
            isLoadingMore = false
        )
    }

    private fun fetchPokemonOverview(batch: Int, isLoadingMore: Boolean) {
        viewModelScope.launch {
            if (isLoadingMore) setStateToLoadingMore() else setStateToLoading()
            performCall(batch)
                .onSuccess { pokemonOverview ->
                    onFetchedPokemons(pokemonOverview)
                }
                .onFailure { throwable ->
                    onFetchFailure(throwable)
                }
        }
    }

    private suspend fun setStateToLoadingMore() {
        val currentState = mutableState.value
        if (currentState is State.Success) {
            mutableState.emit(State.Success(currentState.display, isLoadingMore = true))
        }
    }

    private suspend fun setStateToLoading() {
        mutableState.emit(State.Loading)
    }

    private suspend fun onFetchedPokemons(pokemonOverview: PokemonOverview) {
        fetchedPokemons.addAll(pokemonOverview.results)
        val display = pokemonOverviewDisplayMapper.map(
            domain = pokemonOverview.copy(
                results = fetchedPokemons
            )
        )
        mutableState.emit(State.Success(display, isLoadingMore = false))
    }

    private suspend fun onFetchFailure(
        throwable: Throwable
    ) {
        mutableState.emit(State.Error(throwable.message ?: ""))
    }

    fun fetchMorePokemons() {
        currentBatch += 1
        fetchPokemonOverview(
            batch = currentBatch,
            isLoadingMore = true
        )
    }

    private suspend fun performCall(batch: Int): Result<PokemonOverview> = runCatching {
        service.getAllPokemon(
            limit = FETCH_LIMIT,
            offset = batch * FETCH_LIMIT
        )
    }.mapCatching { response ->
        handleResponse(response, batch)
            .getOrThrow()
    }

    private suspend fun handleResponse(response: Response<PokemonOverviewResponse>, batch: Int): Result<PokemonOverview> = runCatching {
        responseMapper.map(response).map {
            pokemonOverviewMapper.map(it, batch, getFavorites())
        }.getOrThrow()
    }

    private fun buildOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .cache(Cache(context.cacheDir, MAX_CACHE_SIZE))
            .connectTimeout(NETWORK_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(NETWORK_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(NETWORK_TIMEOUT.toLong(), TimeUnit.SECONDS)
        return builder.build()
    }

    private fun buildRetrofit(url: String): Retrofit {
        return Retrofit.Builder().run {
            baseUrl(url)
            addConverterFactory(MoshiConverterFactory.create(moshi))
            client(buildOkHttpClient())
        }.build()
    }

    private fun storeFavorites(list: List<String>) {
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[DATA_STORE_POKEMONS_FAVORITED_KEY] = Json.encodeToString<List<String>>(list)
            }
        }
    }

    fun storeFavorite(id: String) {
        viewModelScope.launch {
            val currentFavorites = getFavorites().toMutableList()
            currentFavorites.add(id)
            storeFavorites(currentFavorites)
        }
    }

    fun removeFavorite(id: String) {
        viewModelScope.launch {
            val currentFavorites = getFavorites().toMutableList()
            currentFavorites.remove(id)
            storeFavorites(currentFavorites)
        }
    }

    private suspend fun getFavorites() : List<String> {
        val flow = context.dataStore.data
            .catch { throwable ->
                println("$TAG - error fetching datastore data: $throwable")
            }
            .map { preferences ->
                preferences[DATA_STORE_POKEMONS_FAVORITED_KEY] ?: null
            }
        try {
            val jsonString = flow.first()
            return jsonString?.let {
                Json.decodeFromString<List<String>>(it)
            } ?: emptyList()
        } catch (exc: Exception) {
            println("$TAG - error while processing data from store: $exc")
            return emptyList()
        }
    }

    //<<<<< Legacy - SharedPreferences >>>>>
    private fun storeFavoritesLegacy(list: List<String>) {
        with(sharedPreferences.edit()) {
            val jsonString = Json.encodeToString<List<String>>(list)
            putString(SHARED_PREFERENCES_POKEMONS_FAVORITED_KEY, jsonString)
        }.apply()
    }

    fun storeFavoriteLegacy(id: String) {
        val currentFavorites = getFavoritesLegacy()
        currentFavorites.add(id)
        storeFavoritesLegacy(currentFavorites)
    }

    fun removeFavoriteLegacy(id: String) {
        val currentFavorites = getFavoritesLegacy()
        currentFavorites.remove(id)
        storeFavoritesLegacy(currentFavorites)
    }

    private fun getFavoritesLegacy(): MutableList<String> {
        with(sharedPreferences) {
            var list: List<String>
            val jsonString = getString(SHARED_PREFERENCES_POKEMONS_FAVORITED_KEY, null)
            list = jsonString?.let { Json.decodeFromString<List<String>>(it) } ?: emptyList()
            return list.toMutableList()
        }
    }
    //>>>>><<<<<

    sealed class State {
        data class Success(
            val display: PokemonOverviewDisplay,
            val isLoadingMore: Boolean
        ) : State()
        data object Loading : State()
        data class Error(
            val exception: String
        ) : State()
        data object Initial : State()
    }
}