package com.wearetriple.exercises.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesStorage @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val Context.favoritesDataStore: DataStore<Preferences> by preferencesDataStore(name = "favorites")

    companion object {
        private val FAVORITES_LIST = stringSetPreferencesKey("favorites")
    }

    fun getFavoriteIds(): Flow<List<Int>> {
        return context.favoritesDataStore.data.map { data ->
            data[FAVORITES_LIST]?.mapNotNull { it.toIntOrNull() }.orEmpty()
        }
    }

    suspend fun addFavorite(id: Int) {
        context.favoritesDataStore.edit { data ->
            val favorites = data[FAVORITES_LIST].orEmpty().toMutableSet()
            favorites.add(id.toString())
            data[FAVORITES_LIST] = favorites
        }
    }

    suspend fun removeFavorite(id: Int) {
        context.favoritesDataStore.edit { data ->
            val favorites = data[FAVORITES_LIST].orEmpty().toMutableSet()
            favorites.remove(id.toString())
            data[FAVORITES_LIST] = favorites
        }
    }
}