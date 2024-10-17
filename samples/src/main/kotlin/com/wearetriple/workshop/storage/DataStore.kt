package com.wearetriple.workshop.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// DataStore needs your Application context, create an extension function to create your DataStore
val Context.myDataStore: DataStore<Preferences> by preferencesDataStore(name = "my_datastore")
                                // ^ Preferences is the name of the framework used by DataStore

class MyStorage @Inject constructor(
    @ApplicationContext private val context: Context // Application context can be injected safely using @ApplicationContext
) {

    companion object {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
    }

    // Function for setting a value
    suspend fun setDarkModeEnabled(enabled: Boolean) {
        context.myDataStore.edit { preferences ->
            preferences[DARK_MODE] = enabled
        }
    }

    // Function for reading a value
    suspend fun getDarkModeEnabled(): Boolean {
        val preferences: Flow<Preferences> = context.myDataStore.data // This is a flow
        return preferences.map { data -> // Use `map` to map the preferences to your read value
            data[DARK_MODE]
        }.first() ?: false // Use `first()` to read the first value of the flow (or default to false when null)
    }
}