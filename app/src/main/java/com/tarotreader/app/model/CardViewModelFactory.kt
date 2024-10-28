package com.tarotreader.app.model

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tarotreader.app.AppSettings

@Suppress("UNCHECKED_CAST")
class CardViewModelFactory(
    private val spread: Spread
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardViewModel::class.java))
            return CardViewModel(spread) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}