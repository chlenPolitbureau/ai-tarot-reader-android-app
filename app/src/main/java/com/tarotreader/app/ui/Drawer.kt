package com.tarotreader.app.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tarotreader.app.PersonalSettings
import com.tarotreader.app.R
import com.tarotreader.app.model.AppViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavDrawer(
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
    username: String,
    scope: CoroutineScope,
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    DismissibleNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DismissibleDrawerSheet(drawerState = drawerState) {
                Column(Modifier.fillMaxHeight()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close"
                            )
                        }
                    }
                    if (username == "") {
                        DrawerAuthCard()
                    } else {

                    }
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
                        onClick = { scope.launch {
                            drawerState.close()
                            navController.navigate(PersonalSettings)
                        } },
                        selected = false
                    )
                    NavigationDrawerItem(
                        label = { Text("Rate The App") },
                        onClick = { scope.launch {
                            drawerState.close()
                            navController.navigate(PersonalSettings)
                        } },
                        selected = false
                    )
                }
            }
        },
        content = {}
    )
}

@Composable
fun DrawerAuthCard(
    @DrawableRes avatar: Int = R.drawable.avatar_lazybones_sloth_svgrepo_com,
    text: String = "Authorize and unlock unlimited possibilities"
) {
    Card() {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = avatar),
                contentDescription = "Avatar",
                modifier = Modifier.padding(2.dp)
            )
            Text(text)
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
    RATE_APP("Rate the app"),
    REMOVE_ADS("Remove ads")
}