package com.tarotreader.app

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tarotreader.app.data.AppRoutes
import com.tarotreader.app.data.BottomAppBarDataSource
import com.tarotreader.app.model.BottomBarMenuItem
import com.tarotreader.app.model.ChatViewModel
import com.tarotreader.app.ui.ChatView
import com.tarotreader.app.ui.ContentTabs
import com.tarotreader.app.ui.ContentViewPage
import com.tarotreader.app.ui.FeatureDescriptionScreen
import com.tarotreader.app.ui.MainScreen
import com.tarotreader.app.ui.NavDrawer
import kotlinx.coroutines.launch


@Composable
fun TarotReaderApp(
    context: Context,
    chatViewModel: ChatViewModel,
    navController: NavHostController = rememberNavController(),
    items: List<BottomBarMenuItem> = BottomAppBarDataSource.items
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
        },
        bottomBar = {
            RealBottomBar(
                currentScreen = currentScreen,
                items = items,
                navController = navController
            )
        }
    ) {
        innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppRoutes.Intro.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            val contentRoute = "content/{type}/{id}"

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
            composable(route = AppRoutes.Learn.name) {
                ContentTabs(
                    navController = navController
                )
            }
            composable(
                route = contentRoute,
                arguments = listOf(
                    navArgument("type") { type = NavType.StringType },
                    navArgument("id") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val type = backStackEntry.arguments?.getString("type")
                val id = backStackEntry.arguments?.getString("id")
                ContentViewPage(type = type, id = id, onClose = {})
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarotReaderBottomBar(
    currentScreen: AppRoutes,
    items: List<BottomBarMenuItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    if(currentScreen != AppRoutes.Intro) {
        LazyRow (
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items.size) {
                    index ->
                BottomAppBarItem(
                    text = items[index].title,
                    icon = items[index].icon,
                    onClick = { navController.navigate(items[index].route) })
            }
        }
    }
}

@Composable
fun BottomAppBarItem(
    @StringRes text: Int,
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        IconButton(
            onClick = onClick
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = text.toString(),
                modifier = Modifier
                    .clip(RectangleShape)
                    .padding(4.dp)
            )
        }
        Text(stringResource(text))
    }
}

@Composable
fun RealBottomBar(
    currentScreen: AppRoutes,
    items: List<BottomBarMenuItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    if(currentScreen != AppRoutes.Intro) {
        NavigationBar(
            modifier = modifier.height(100.dp)
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.title.toString(),
                            modifier = Modifier.size(28.dp)
                        )
                    },
                    label = { Text(stringResource(item.title)) },
                    onClick = { navController.navigate(item.route) },
                    selected = currentScreen.name == item.route
                )
            }
        }
    }
}