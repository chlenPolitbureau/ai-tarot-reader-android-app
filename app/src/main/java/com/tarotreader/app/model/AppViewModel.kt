package com.tarotreader.app.model

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarotreader.app.AppSettings
import com.tarotreader.app.data.DSManager
import com.tarotreader.app.data.DailyAdviceSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

class AppViewModel(
    private val appSettingsDataStoreManager: DSManager,
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    private val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)
    private val nowTimestampMillis = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()

    private val _uiState = MutableStateFlow(
        AppSettings()
    )

    val uiState: StateFlow<AppSettings> = _uiState.asStateFlow()
    val dailyAdviceList = DailyAdviceSource.listOfAdvice

    init {
        viewModelScope.launch {
            appSettingsDataStoreManager.appSettingsFlow.collect {
                _uiState.value = it
            }
            println("Timestamp after load, before update: ${_uiState.value.lastSessionDateTimeMilliSec}")
            if (_uiState.value.lastSessionDateTimeMilliSec < 1) {
                updateSessionTimeStamp(nowTimestampMillis)
            }
            println("Timestamp after load, after update: ${_uiState.value.lastSessionDateTimeMilliSec}")
        }
    }

    fun formatMillisToDateTimeUTC(milliseconds: Long, pattern: String): String {
        val instant: Instant = Instant.ofEpochMilli(milliseconds)
        val offsetDateTime = instant.atOffset(ZoneOffset.UTC)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
        return offsetDateTime.format(formatter)
    }

    suspend fun sessionLaunch(
        lastSessionMillis: Long,
        nowMillis: Long,
        interval: Long = 144000000,
        currencyType: CurrencyType = CurrencyType.MANA,
        amount: Long = 30
    ) {
        viewModelScope.launch {
            println(
                "Evaluation result: ${nowMillis - lastSessionMillis} > $interval"
            )
            println("Formatted datetime now is ${formatMillisToDateTimeUTC(nowMillis, "yyyy-MM-dd HH:mm:ss")}")
            println("Formatted datetime last is ${formatMillisToDateTimeUTC(lastSessionMillis, "yyyy-MM-dd HH:mm:ss")}")
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