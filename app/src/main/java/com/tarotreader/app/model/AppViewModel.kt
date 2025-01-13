package com.tarotreader.app.model

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarotreader.app.AppSettings
import com.tarotreader.app.data.ChatDataSource
import com.tarotreader.app.data.PredictRequest
import com.tarotreader.app.data.RetrofitClient
import com.tarotreader.app.ui.ShuffleDeck
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


class AppViewModel(
    val dataStore: DataStore<AppSettings>,
): ViewModel() {
    val currentUserName = dataStore.data.map {
        appSettings -> appSettings.userName
    }

    val currentManaPoints = dataStore.data.map {
        appSettings -> appSettings.manaPoints
    }

    val lastVisitDate = dataStore.data.map {
        appSettings -> appSettings.lastVisitDate
    }

    suspend fun updateMana(balance: Int, manaPoints: Int) {
        viewModelScope.launch {
            dataStore.updateData {
                it.copy(
                    manaPoints = balance + manaPoints
                )
            }
        }
    }

    fun updatePersonalSettings(
        name: String,
        gender: String,
        dateOfBirth: String
        ) {
        viewModelScope.launch {
            dataStore.updateData {
                it.copy(
                    userName = name,
                    gender = gender,
                    dateOfBirth = dateOfBirth
                )
            }
        }
    }

    suspend fun writePrediction(prediction: Prediction) {
        viewModelScope.launch {
            dataStore.updateData {
                it.copy(
                    predictions = it.predictions.mutate {
                        it.add(prediction)
                    }
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun updateLastVisitDate(date: String) {
        viewModelScope.launch {
            dataStore.updateData {
                it.copy(
                    lastVisitDate = date
                )
            }
        }
    }
}