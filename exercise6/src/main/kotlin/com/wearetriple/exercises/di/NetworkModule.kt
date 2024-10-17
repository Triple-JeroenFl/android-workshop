package com.wearetriple.exercises.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.wearetriple.exercises.network.service.PokemonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    fun providePokemonService(moshi: Moshi): PokemonService = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(PokemonService::class.java)
}