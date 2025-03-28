package com.tarotreader.app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.ads.MobileAds
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.tarotreader.app.data.DSManager
import com.tarotreader.app.data.PreferencesManager
import com.tarotreader.app.model.AppViewModel
import com.tarotreader.app.model.AppViewModelFactory
import com.tarotreader.app.ui.theme.TarotReaderTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                false
            }
        }
        val backgroundScope = CoroutineScope(Dispatchers.IO)
        backgroundScope.launch {
            MobileAds.initialize(this@MainActivity) {}
        }
        enableEdgeToEdge()
        setContent {
            TarotReaderTheme {
                val dataStoreManager = DSManager(this)
                val preferencesManager = PreferencesManager(this)

                val viewModelFactory = AppViewModelFactory(
                    dataStoreManager,
                    preferencesManager
                )
                val appViewModel = ViewModelProvider(this, viewModelFactory)[AppViewModel::class.java]
                val isFirstLaunch = appViewModel.isFirstLaunch.collectAsStateWithLifecycle()
                println(
                    "MainActivity first launch: ${isFirstLaunch.value}"
                )
                val startingScreen = if(isFirstLaunch.value) Intro else Main

                TarotReaderApp(
                    appViewModel = appViewModel,
                    startingScreen = startingScreen
                )
            }
        }
    }
}