package com.wearetriple.exercises.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import com.wearetriple.exercises.ui.model.JokeResponse
import com.wearetriple.exercises.ui.service.JokeService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import java.util.concurrent.TimeUnit

@HiltViewModel
class JokeViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val moshi: Moshi,
): ViewModel() {

    companion object {
        private const val MAX_CACHE_SIZE = 10 * 1024 * 1024L
        private const val NETWORK_TIMEOUT = 30
        private const val BASE_URL = "https://v2.jokeapi.dev/joke/"
    }

    private val mutableJoke: MutableStateFlow<JokeResponse?> = MutableStateFlow(null)
    val joke: StateFlow<JokeResponse?> = mutableJoke.asStateFlow()

    private val retrofit = buildRetrofit(BASE_URL)
    private val service = retrofit.create(JokeService::class.java)

    init {
        fetchJoke()
    }

    private fun fetchJoke() {
        viewModelScope.launch {
            mutableJoke.emit(null)
            val response = service.getJoke()
            mutableJoke.emit(response)
        }
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
}