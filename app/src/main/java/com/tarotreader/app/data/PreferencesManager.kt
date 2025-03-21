package com.tarotreader.app.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.preferencesDataStore by preferencesDataStore("app-settings.json")

class PreferencesManager(val context: Context) {
    val appPreferencesFlow: Flow<Preferences> = context.preferencesDataStore.data
    companion object {
        val isFirstOpen = booleanPreferencesKey("is_first_open")
    }

    val isFirstLaunch = appPreferencesFlow.map { prefs ->
        prefs[isFirstOpen] ?: true
    }

    suspend fun saveBoolKeyToDataStore(
        key: Preferences.Key<Boolean>,
        value: Boolean
    ) {
        context.preferencesDataStore.edit {
            it[key] = value
        }
    }

    suspend fun getBoolKeyFromDatastore(
        key: Preferences.Key<Boolean>
    ): Boolean {
        val preferences = appPreferencesFlow.first()
        return if (preferences[key] != null) {
            preferences[key]!!
        } else {
            true
        }
    }
}
