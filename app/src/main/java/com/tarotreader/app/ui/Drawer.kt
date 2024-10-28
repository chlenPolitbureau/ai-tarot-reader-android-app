package com.tarotreader.app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tarotreader.app.PersonalSettings
import com.tarotreader.app.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavDrawer(
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    navController: NavHostController,
    content: @Composable () -> Unit
) {

    val items = DrawerItems.entries

    DismissibleNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DismissibleDrawerSheet(drawerState = drawerState) {
                Column(Modifier.fillMaxHeight()) {
                    DrawerAuthCard()
                    NavigationDrawerItem(
                        label = { Text("Personal Settings") },
                        onClick = { scope.launch {
                            drawerState.close()
                            navController.navigate(PersonalSettings)
                        } },
                        selected = false
                    )
                    NavigationDrawerItem(
                        label = { Text("Reading Settings") },
                        onClick = { scope.launch { navController.navigate(PersonalSettings) } },
                        selected = false
                    )
                    NavigationDrawerItem(
                        label = { Text("Rate App") },
                        onClick = { scope.launch { navController.navigate(PersonalSettings) } },
                        selected = false
                    )
                }
            }
        },
        content = {}
    )
}

@Composable
fun DrawerAuthCard() {
    Card() {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Avatar",
                modifier = Modifier.padding(2.dp)
            )
            Text("Authorize and unlock unlimited possibilities")
            Button(
                onClick = { /*TODO*/ }
            ) {
                Text("Authorize")
            }
        }
    }
}

enum class DrawerItems(
//    val icon: Int,
    val label: String
) {
    ACCOUNT_SETTINGS("Personal settings"),
    READING_SETTINGS("Reading settings"),
    RATE_APP("Rate app"),
    REMOVE_ADS("Remove ads")
}