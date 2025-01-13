package com.tarotreader.app

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.tarotreader.app.model.AppViewModel
import com.tarotreader.app.model.AppViewModelFactory
import com.tarotreader.app.model.Prediction
import com.tarotreader.app.ui.theme.TarotReaderTheme
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.launch
import java.time.LocalDateTime

val Context.dataStore by dataStore("settings.json", AppSettingsSerializer)

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TarotReaderTheme {
                val dataStore = dataStore
                val viewModelFactory = AppViewModelFactory(dataStore)
                val appViewModel = ViewModelProvider(this, viewModelFactory)[AppViewModel::class.java]
                val todayString = LocalDateTime.now().toString().substring(
                    range = 0..9
                )
                val dataStoreState = dataStore.data.collectAsState(
                    initial = AppSettings()
                )

                val lastVisitDateString = dataStoreState.value.lastVisitDate
                val manaBalance = dataStoreState.value.manaPoints

                val sharedPrefs = getSharedPreferences("com.tarotreader.app.prefs_key_values",
                    Context.MODE_PRIVATE)

                LaunchedEffect(
                    key1 = Unit
                ) {
                    if (lastVisitDateString != todayString) {
                        appViewModel.updateMana(manaBalance, 44)
                        appViewModel.updateLastVisitDate(todayString)
                        appViewModel.updatePersonalSettings(
                            name = "Synthetic Guest",
                            gender = "male",
                            dateOfBirth = "1970-01-01"
                        )
                    }
                }

                TarotReaderApp(
                    sharedPrefs = sharedPrefs,
                    dataStore = dataStore,
                    appViewModel = appViewModel
                )
            }
        }
    }
}