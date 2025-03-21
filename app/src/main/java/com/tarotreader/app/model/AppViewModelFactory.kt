package com.tarotreader.app.model

import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tarotreader.app.AppSettings
import com.tarotreader.app.data.DSManager
import com.tarotreader.app.data.PreferencesManager

@Suppress("UNCHECKED_CAST")
class AppViewModelFactory(
    private val dataStoreManager: DSManager,
    private val preferencesManager: PreferencesManager
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java))
            return AppViewModel(dataStoreManager, preferencesManager) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}