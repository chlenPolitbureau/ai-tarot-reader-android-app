package com.tarotreader.app.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarotreader.app.data.DSManager
import kotlinx.coroutines.launch

class AppViewModel(
    private val appSettingsDataStoreManager: DSManager
): ViewModel() {

    val appSettingsFlow = appSettingsDataStoreManager.appSettingsFlow

    suspend fun updatePersonalSettings(
        name: String,
        gender: String,
        dateOfBirth: String
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

    fun updateCurrency(type: CurrencyType, amount: Long) {
        viewModelScope.launch {
            appSettingsDataStoreManager.changeCurrency(
                currencyType = type,
                amount = amount
            )
        }
    }
}