package com.wearetriple.exercises.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.wearetriple.exercises.model.JokeResponse
import com.wearetriple.exercises.service.JokeService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

@HiltViewModel
class JokeViewModel @Inject constructor() : ViewModel() {

    companion object {
        private const val BASE_URL = "http://www.official-joke-api.appspot.com/"
    }

    private val mutableJoke: MutableStateFlow<JokeResponse?> = MutableStateFlow(null)
    val joke: StateFlow<JokeResponse?> = mutableJoke.asStateFlow()

    private val converter = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(converter))
        .build()
    private val service = retrofit.create(JokeService::class.java)

    fun fetchJoke() {
        viewModelScope.launch {
            val response = service.getJoke()
            mutableJoke.emit(response)
        }
    }
}