package com.wearetriple.workshop.networking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.map
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

// Define a pager source
class PokedexPagingSource(
    private val service: PokemonService
) : PagingSource<Int, PokemonResponse>() {

    companion object {
        const val PAGE_SIZE = 20
    }

    // Refresh key determines which set of data should be fetched when the source is invalidated
    override fun getRefreshKey(state: PagingState<Int, PokemonResponse>): Int? {
        // Determine the offset based on the current position in the data set
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(PAGE_SIZE)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(PAGE_SIZE)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResponse> {
        val offset = params.key ?: 0 // If no offset has been set, take 0
        return try {
            val response = service.getPokemonList(offset = offset, limit = PAGE_SIZE)
            LoadResult.Page(
                data = response.results,
                prevKey = if (response.previous != null) offset - PAGE_SIZE else null, // Safe set previous offset value
                nextKey = if (response.next != null) offset + PAGE_SIZE else null // Safe set next offset value
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }
}

class PokemonMapper @Inject constructor() {

    fun map(response: PokemonResponse): Pokemon {
        val id = response.url.split("/").dropLast(1).last().toInt()
        val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
        return Pokemon(
            id = id,
            name = response.name,
            imageUrl = imageUrl
        )
    }
}

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val mapper: PokemonMapper,
) : ViewModel() {

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/"
    }

    private val converter = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(converter))
        .build()
    private val service = retrofit.create(PokemonService::class.java)

    /*
    A Flow type is the parent type of StateFlow or SharedFlow. Pager is the primary entry point
    to using a paging source.
     */
    val state: Flow<PagingData<Pokemon>> = Pager(PagingConfig(pageSize = PokedexPagingSource.PAGE_SIZE)) {
        PokedexPagingSource(service)
    }.flow
        .cachedIn(viewModelScope)
        .map { data ->
            data.map(mapper::map)
        }
}

@Composable
fun PokemonListScreen(
    viewModel: PokedexViewModel = hiltViewModel()
) {
    // Special `collectAs` for paging data
    val state = viewModel.state.collectAsLazyPagingItems()
    Box(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) { // Lazy column for performance
            items(state.itemCount) { index ->
                state[index]?.let { pokemon ->
                    Text(text = pokemon.name)
                }
            }
        }
    }
}