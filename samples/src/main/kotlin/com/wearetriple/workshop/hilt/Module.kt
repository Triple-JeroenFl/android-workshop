package com.wearetriple.workshop.hilt

import androidx.lifecycle.ViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.wearetriple.workshop.networking.PokemonListMapper
import com.wearetriple.workshop.networking.PokemonService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import javax.inject.Inject

// Define your custom module
@Module
@InstallIn(SingletonComponent::class) // Install in singleton component, means only one instance per provided binding
object ApiModule {

    private const val BASE_URL = "https://pokeapi.co/api/"

    // Define Moshi here so you only have to define it once, and inject and reuse elsewhere
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
        .add(KotlinJsonAdapterFactory())
        .build()

    // The moshi argument here is injected
    @Provides
    fun providePokemonService(moshi: Moshi): PokemonService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(PokemonService::class.java)
}

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val service: PokemonService,
    private val mapper: PokemonListMapper
) : ViewModel() {

}

// An interface does not have a constructor thus cannot be constructed
interface SomeInterface

// This interface implementation can be constructed
class SomeClass @Inject constructor() : SomeInterface

@Module
@InstallIn(ActivityComponent::class) // Install in ActivityComponent means it has one instance per activity scope
object SomeModule {

    // Use @Binds to define which class should be injected when SomeInterface is requested
    @Binds
    fun bindSomeInterface(someClass: SomeClass): SomeInterface = someClass
}