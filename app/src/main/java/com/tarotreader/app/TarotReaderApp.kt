package com.tarotreader.app

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.tarotreader.app.data.BottomBarMenuItem
import com.tarotreader.app.model.AppViewModel
import com.tarotreader.app.model.CurrencyType
import com.tarotreader.app.ui.ContentTabs
import com.tarotreader.app.ui.ContentViewPage
import com.tarotreader.app.ui.FeatureDescriptionScreen
import com.tarotreader.app.ui.MainScreen
import com.tarotreader.app.ui.NavDrawer
import com.tarotreader.app.ui.PersonalSettingsScreen
import com.tarotreader.app.ui.PredictionHistoryView
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TarotReaderApp(
    startingScreen: kotlin.Any,
    appViewModel: AppViewModel,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination
        ?.route?.removePrefix("com.tarotreader.app.") ?: ""
    val screenTitle = remember { mutableStateOf("") }
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("MMMM, dd")
    val showBars = remember { mutableStateOf(true) }
    val showBackArrow = currentScreen != "Main"
    val contentType = remember { mutableStateOf("") }

    val dataStoreState by appViewModel.uiState.collectAsState()

    val userManaPoints = dataStoreState.currencies.first {
        it.type == CurrencyType.MANA
    }.amount
    val userName = dataStoreState.userName

    fun updateContentType(name: String) {
        contentType.value = name
    }

    if (currentScreen == "Intro") {
            showBars.value = false
        }
    else if (currentScreen == "Main") {
            screenTitle.value = currentDate.format(formatter)
            showBars.value = true
        }
    else if (currentScreen.contains("Content") ) {
            screenTitle.value = "About the ${contentType.value}"
        }
    else if (currentScreen.contains("Chat") ) {
        screenTitle.value = "Chat"
    }
    else {
        screenTitle.value = currentScreen
    }

        Scaffold(
            topBar = {
                if(showBars.value) {
                    TarotReaderAppBar(
                        currentScreen = screenTitle.value,
                        navController = navController,
                        showBackArrow = showBackArrow,
                        userManaPoints = userManaPoints
                    )
                }
            },
            bottomBar = {
                if(showBars.value) {
                    RealBottomBar(
                        currentScreen = currentScreen,
                        navController = navController
                    )
                }
            }
        ) {
            innerPadding ->
            NavHost(
                navController = navController,
                startDestination = startingScreen,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable<Intro> {
                    FeatureDescriptionScreen(
                        navController = navController,
                        appViewModel = appViewModel
                    )
                }
                composable<Main> {
                    MainScreen(
                        appViewModel = appViewModel,
                        navController = navController
                    )
                }
                composable<Learn> {
                    val args = it.toRoute<Learn>()
                    val startingTab = args.startingTab ?: 0
                    ContentTabs(
                        navController = navController,
                        startingTab = startingTab
                    )
                }
                composable<PersonalSettings> {
                    PersonalSettingsScreen(
                        appViewModel = appViewModel
                    )
                }
                composable<Journal> {
                    PredictionHistoryView(
                        predictions = dataStoreState.predictions
                    )
                }
                composable<Content> {
                    val args = it.toRoute<Content>()
                    ContentViewPage(
                        type = args.type,
                        id = args.id,
                        postback = ::updateContentType,
                        onClose = {},
                        navController = navController
                    )
                }
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarClass(
    screenTitle: String,
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    navController: NavHostController,
    showBackArrow: Boolean,
    manaPoints: Long
) {
    val scope = rememberCoroutineScope()

    TopAppBar(
        title = { Text(
            screenTitle
            ) },
        navigationIcon = {
            if (navController.previousBackStackEntry != null && showBackArrow) {
                IconButton(
                    onClick = { navController.navigateUp() },
                    content = {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
                )
            }
        },
        actions = {
            Icon(
                painter = painterResource(id = R.drawable.mana),
                contentDescription = "Mana",
                modifier = Modifier.size(40.dp),
                tint = Color.Unspecified,
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = manaPoints.toString(),
                textAlign = TextAlign.End,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(
                onClick = { scope.launch { drawerState.open() } },
                content = {
                    Icon(
                        painter = painterResource(R.drawable.list),
                        contentDescription = "Avatar",
                        modifier = Modifier.padding(2.dp)
                    )
                }
            )
        },

        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarotReaderAppBar(
    currentScreen: String,
    showBackArrow: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    userManaPoints: Long
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    TopAppBarClass(currentScreen, modifier, drawerState, navController, showBackArrow, userManaPoints)
    NavDrawer(
        drawerState = drawerState,
        scope = scope,
        navController = navController,
    ) {
    }
}

@Composable
fun RealBottomBar(
    currentScreen: String,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomBarMenuItem(
            text = R.string.home,
            icon = R.drawable.houseline,
            navigate = { navController.navigate(Main) }
        ),
        BottomBarMenuItem(
            text = R.string.learn,
            icon = R.drawable.graduationcap,
            navigate = { navController.navigate(Learn(startingTab = 0)) }
        ),
        BottomBarMenuItem(
            text = R.string.journal,
            icon = R.drawable.scroll,
            navigate = { navController.navigate(Journal) }
        )
    )
    NavigationBar(
        modifier = modifier.height(100.dp)
        ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.text.toString(),
                        modifier = Modifier.size(28.dp)
                        )
                       },
                label = { Text(stringResource(item.text)) },
                onClick = item.navigate,
                selected = currentScreen == item.text.toString()
            )
        }
    }
}

@Serializable
object Intro

@Serializable
object Main

@Serializable
object Journal

@Serializable
data class Learn(
    val startingTab: Int? = 0
)

@Serializable
object PersonalSettings

@Serializable
data class Content(
    val type: String,
    val id: String
)