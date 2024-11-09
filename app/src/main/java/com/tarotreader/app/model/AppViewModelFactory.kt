package com.tarotreader.app.model

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tarotreader.app.AppSettings

@Suppress("UNCHECKED_CAST")
class AppViewModelFactory(
    private val dataStore: DataStore<AppSettings>
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java))
            return AppViewModel(dataStore) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}