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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

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

    val state = Pager(PagingConfig(pageSize = PokedexPagingSource.PAGE_SIZE)) {
        PokedexPagingSource(service)
    }.flow
        .cachedIn(viewModelScope)
        .map { data ->
            data.map(mapper::map)
        }
}