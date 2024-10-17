package com.wearetriple.exercises.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.wearetriple.exercises.mapper.PokemonMapper
import com.wearetriple.exercises.network.paging.PokedexPagingSource
import com.wearetriple.exercises.network.service.PokemonService
import com.wearetriple.exercises.storage.FavoritesStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val mapper: PokemonMapper,
    private val service: PokemonService,
    private val storage: FavoritesStorage,
) : ViewModel() {

    val state = Pager(PagingConfig(pageSize = PokedexPagingSource.PAGE_SIZE)) {
        PokedexPagingSource(service)
    }.flow
        .cachedIn(viewModelScope)
        .combine(storage.getFavoriteIds()) { pokemon, favorites ->
            pokemon to favorites
        }
        .map { (data, favorites) ->
            data.map { pokemon ->
                mapper.map(pokemon, favorites)
            }
        }

    fun toggleFavorite(id: Int, favorite: Boolean) {
        viewModelScope.launch {
            if (favorite) {
                storage.addFavorite(id)
            } else {
                storage.removeFavorite(id)
            }
        }
    }
}