package com.tarotreader.app

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.tarotreader.app.data.DSManager
import com.tarotreader.app.model.AppViewModel
import com.tarotreader.app.model.AppViewModelFactory
import com.tarotreader.app.ui.theme.TarotReaderTheme


//val Context.dataStore by dataStore("settings.json", AppSettingsSerializer)

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TarotReaderTheme {
                val dataStoreManager = DSManager(this)
                val viewModelFactory = AppViewModelFactory(
                    dataStoreManager
                )
                val appViewModel = ViewModelProvider(this, viewModelFactory)[AppViewModel::class.java]


                val sharedPrefs = getSharedPreferences("com.tarotreader.app.prefs_key_values",
                    Context.MODE_PRIVATE)


                TarotReaderApp(
                    sharedPrefs = sharedPrefs,
                    appViewModel = appViewModel
                )
            }
        }
    }
}