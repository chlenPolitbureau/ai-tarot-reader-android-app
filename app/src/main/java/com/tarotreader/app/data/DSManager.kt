package com.tarotreader.app.data

import android.content.Context
import androidx.datastore.dataStore
import com.tarotreader.app.AppSettings
import com.tarotreader.app.AppSettingsSerializer
import com.tarotreader.app.model.CurrencyType
import com.tarotreader.app.model.Prediction
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDate

val Context.appSettingsDataStore by dataStore("settings.json", AppSettingsSerializer)

class DSManager(val context: Context) {

    val appSettingsFlow: Flow<AppSettings> = context.appSettingsDataStore.data

    suspend fun saveDataStore(data: AppSettings) {
        context.appSettingsDataStore.updateData {
            it.copy(
                predictions = data.predictions,
                language = data.language,
                userName = data.userName,
                gender = data.gender,
                currencies = data.currencies
            )
        }
    }

    suspend fun updatePersonalData(
        name: String,
        gender: String,
        dateOfBirth: Long
    ) {
        context.appSettingsDataStore.updateData {
            it.copy(
                userName = name,
                gender = gender,
                dateOfBirth = dateOfBirth
            )
        }
    }

    suspend fun updateLastSession(
        timeStampMillis: Long
    ) {
        context.appSettingsDataStore.updateData {
            it.copy(
                lastSessionDateTimeMilliSec = timeStampMillis
            )
        }
    }

    suspend fun updateGender(
        gender: String
    ) {
        context.appSettingsDataStore.updateData {
            it.copy(
                gender = gender
            )
        }
    }

    suspend fun writePrediction(prediction: Prediction) {
        context.appSettingsDataStore.updateData {
            it.copy(
                predictions = it.predictions.mutate {
                    it.add(prediction)
                }
            )
        }
    }

    suspend fun changeCurrency(amount: Long, currencyType: CurrencyType) {
        context.appSettingsDataStore.updateData {
            it.copy(
                currencies = it.currencies.map {
                    if (it.type == currencyType) {
                        it.copy(amount = it.amount + amount)
                    } else {
                        it
                    }
                }.toPersistentList()
            )
        }
    }

}