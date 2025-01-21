package com.tarotreader.app.model

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tarotreader.app.AppSettings
import com.tarotreader.app.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

data class Currency (
    val type: CurrencyType,
    var amount: Long = 0
)

enum class CurrencyType {
    MANA, ENERGY, COINS
}

class CurrencyManager(private val dataStoreManager: DataStoreManager) {
    private val _currencies = mutableStateListOf<Currency>()
    val currencies: List<Currency> = _currencies

    init {
        loadCurrencies()
    }

    private fun loadCurrencies() {
        // Load currencies from DataStore or other persistence mechanism
        // Example:
        val loadedCurrencies = dataStoreManager.loadCurrencies()
        _currencies.addAll(loadedCurrencies)
    }

    fun addCurrency(type: CurrencyType, amount: Long) {
        if (amount <= 0) {
            throw IllegalArgumentException("Amount must be positive")
        }

        val currency = _currencies.find { it.type == type }
        if (currency != null) {
            currency.amount += amount
        } else {
            _currencies.add(Currency(type, amount))
        }
        saveCurrencies()
    }

    fun subtractCurrency(type: CurrencyType, amount: Long): Boolean {
        if (amount <= 0) {
            throw IllegalArgumentException("Amount must be positive")
        }

        val currency = _currencies.find { it.type == type }
        if (currency != null && currency.amount >= amount) {
            currency.amount -= amount
            saveCurrencies()
            return true
        }
        return false
    }

    fun getCurrencyAmount(type: CurrencyType): Long {
        return _currencies.find { it.type == type }?.amount ?: 0
    }

    private fun saveCurrencies() {
        // Save currencies to DataStore or other persistence mechanism
        // Example:
        dataStoreManager.saveCurrencies(_currencies)
    }
}

class DataStoreManager(private val context: Context) {

    private val dataStore: DataStore<AppSettings> = context.dataStore
    val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = "currencies")

    private val currencyKey = stringPreferencesKey("currencies")

    fun saveCurrencies(currencies:List<Currency>) {
        val json = Json.encodeToString(currencies)
        runBlocking {
            context.preferencesDataStore.edit {
                it[currencyKey] = json
            }
        }
    }

    fun loadCurrencies(): List<Currency> {
        var currencies = emptyList<Currency>()
        runBlocking {
            val preferences = context.preferencesDataStore.data.first()
            val json = preferences[currencyKey]
            if (json != null) {
                currencies = Json.decodeFromString<List<Currency>>(json)
            }
        }
        return currencies
    }
}