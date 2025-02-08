package com.tarotreader.app.model

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tarotreader.app.AppSettings
import com.tarotreader.app.data.DSManager

@Suppress("UNCHECKED_CAST")
class AppViewModelFactory(
    private val dataStoreManager: DSManager,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java))
            return AppViewModel(dataStoreManager) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}