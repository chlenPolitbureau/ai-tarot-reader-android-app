package com.tarotreader.app.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarotreader.app.AppSettings
import com.tarotreader.app.data.DSManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel(
    private val appSettingsDataStoreManager: DSManager
): ViewModel() {
    private val _uiState = MutableStateFlow(
        AppSettings()
    )
    val uiState: StateFlow<AppSettings> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            appSettingsDataStoreManager.appSettingsFlow.collect {
                _uiState.value = it
            }
        }
    }

    suspend fun sessionLaunch(
        lastSessionMillis: Long,
        nowMillis: Long,
        interval: Long = 144000000,
        currencyType: CurrencyType = CurrencyType.MANA,
        amount: Long = 31
    ) {
        viewModelScope.launch {
            if ((nowMillis - lastSessionMillis) > interval) {
                updateCurrency(
                    type = currencyType,
                    amount = amount
                )
                updateSessionTimeStamp(nowMillis)
            }
        }
    }

    suspend fun updateGender(gender: String) {
        viewModelScope.launch {
            appSettingsDataStoreManager.updateGender(gender)
        }
    }

    suspend fun updatePersonalSettings(
        name: String,
        gender: String,
        dateOfBirth: Long
    ) {
        viewModelScope.launch {
            appSettingsDataStoreManager.updatePersonalData(
                name = name,
                gender = gender,
                dateOfBirth = dateOfBirth
            )
        }
    }

    suspend fun writePrediction(prediction: Prediction) {
        viewModelScope.launch {
            appSettingsDataStoreManager.writePrediction(prediction)
        }
    }

//    suspend fun writePrediction(prediction: Prediction) {
//        viewModelScope.launch {
//            dataStore.updateData {
//                it.copy(
//                    predictions = it.predictions.mutate {
//                        it.add(prediction)
//                    }
//                )
//            }
//        }
//    }

    fun updateSessionTimeStamp(ts: Long) {
        viewModelScope.launch {
            appSettingsDataStoreManager.updateLastSession(ts)
        }
    }

    fun updateCurrency(type: CurrencyType, amount: Long) {
        viewModelScope.launch {
            appSettingsDataStoreManager.changeCurrency(
                currencyType = type,
                amount = amount
            )
        }
    }
}