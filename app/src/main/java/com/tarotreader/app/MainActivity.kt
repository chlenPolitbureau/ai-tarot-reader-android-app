package com.tarotreader.app

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.tarotreader.app.data.DSManager
import com.tarotreader.app.model.AppViewModel
import com.tarotreader.app.model.AppViewModelFactory
import com.tarotreader.app.ui.theme.TarotReaderTheme
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset


//val Context.dataStore by dataStore("settings.json", AppSettingsSerializer)

class MainActivity : ComponentActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
//    val dataStoreManager = DSManager(this)

//    val viewModelFactory = AppViewModelFactory(
//        dataStoreManager,
//        sharedPrefs
//    )
//    val appViewModel = ViewModelProvider(this, viewModelFactory)[AppViewModel::class.java]


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                false
            }
        }
        enableEdgeToEdge()
        setContent {
            TarotReaderTheme {
                val nowTimestampMillis = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()
                val dataStoreManager = DSManager(this)
                val sharedPrefs = getSharedPreferences("com.tarotreader.app.prefs_key_values",
                    Context.MODE_PRIVATE
                )
                if (!sharedPrefs.contains("isFirstLaunch")) {
                    sharedPrefs.edit().putBoolean("isFirstLaunch", true).apply()
                }

                if (!sharedPrefs.contains("lastSessionDateTimeMilliSec")) {
                    sharedPrefs.edit().putLong("lastSessionDateTimeMilliSec", nowTimestampMillis).apply()
                }
                val viewModelFactory = AppViewModelFactory(
                    dataStoreManager,
                    sharedPrefs
                )
                val appViewModel = ViewModelProvider(this, viewModelFactory)[AppViewModel::class.java]

                TarotReaderApp(
                    sharedPrefs = sharedPrefs,
                    appViewModel = appViewModel
                )
            }
        }
    }
}