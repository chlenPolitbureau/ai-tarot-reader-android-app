package com.tarotreader.app

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tarotreader.app.data.AppRoutes
import com.tarotreader.app.model.ChatMessage
import com.tarotreader.app.model.ChatViewModel
import com.tarotreader.app.ui.ChatView
import com.tarotreader.app.ui.FeatureDescriptionScreen
import com.tarotreader.app.ui.MainScreen
import com.tarotreader.app.ui.NavDrawer
import kotlinx.coroutines.launch

/**
 * Routs definition
 */

@Composable
fun TarotReaderApp(
    context: Context,
    chatViewModel: ChatViewModel,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppRoutes.valueOf(
        backStackEntry?.destination?.route ?: AppRoutes.Main.name
    )

    Scaffold(
        topBar = {
            TarotReaderAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) {
        innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppRoutes.Intro.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = AppRoutes.Intro.name) {
                FeatureDescriptionScreen(
                    onNextButtonClicked = {
                        navController.navigate(AppRoutes.Main.name)
                    }
                )
            }
            composable(route = AppRoutes.Main.name) {
                MainScreen(
                    navController = navController
                )
            }
            composable(route = AppRoutes.Chat.name) {
                ChatView(
                    chatViewModel = chatViewModel
                )
            }
            composable(route = AppRoutes.Test.name) {
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarClass(
        modifier: Modifier = Modifier,
        drawerState: DrawerState
        ) {
    val scope = rememberCoroutineScope()

    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(
                onClick = { scope.launch { drawerState.open() } },
                content = {
                    Icon(
                        painter = painterResource(R.drawable.playing_cards_svgrepo_com),
                        contentDescription = "Avatar",
                        modifier = Modifier.padding(2.dp)
                    )
                }
            )
        },
        actions = {

        },

        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarotReaderAppBar(
    currentScreen: AppRoutes,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    if(currentScreen != AppRoutes.Intro) {
        TopAppBarClass(modifier, drawerState)
        NavDrawer(
            drawerState = drawerState,
            scope = scope) {
        }
    }
}