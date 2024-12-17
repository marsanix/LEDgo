package com.marsanix.ledgo.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.marsanix.ledgo.R
import com.marsanix.ledgo.presentation.SettingAlertDialog
import com.marsanix.pelajarankuapp.SideBarNavigationItem
import com.marsanix.pelajarankuapp.SideBarNavigationItemView

@Composable
fun CustomDrawer(
    selectedNavigationItem: SideBarNavigationItem,
    onNavigationItemClick: (SideBarNavigationItem) -> Unit,
    onCloseClick: () -> Unit
) {

    val openSetting: MutableState<Boolean> = remember { mutableStateOf(false) }
    val settingAlertDialog = SettingAlertDialog(openSetting)

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(fraction = 0.75f)
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back Arrow Icon",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "My Logo"
        )
        Spacer(modifier = Modifier.height(40.dp))

        var drawerState by remember { mutableStateOf(CustomDrawerState.Closed) }
        BackHandler(enabled = drawerState.isOpened()) {
            drawerState = CustomDrawerState.Closed
        }
        val screenHeight = LocalConfiguration.current.screenHeightDp

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height((screenHeight / 1.8).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {


                val nItems = SideBarNavigationItem.entries.size
                SideBarNavigationItem.entries.toTypedArray().take(nItems - 1).forEach { navigationItem ->
                    SideBarNavigationItemView(
                        navigationItem = navigationItem,
                        selected = navigationItem == selectedNavigationItem,
                        onClick = {
                            onNavigationItemClick(navigationItem)

                            // drawerState.opposite()
                            // drawerState = CustomDrawerState.Closed

                        }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        SideBarNavigationItem.entries.toTypedArray().takeLast(1).forEach { navigationItem ->
            SideBarNavigationItemView(
                navigationItem = navigationItem,
                selected = false,
                onClick = {

                    when (navigationItem) {
                        SideBarNavigationItem.Materi1 -> {
                            onNavigationItemClick(SideBarNavigationItem.Materi1)
                        }

                        else -> {}
                    }

                    openSetting.value = true
                    settingAlertDialog.let {
                        openSetting
                    }
                    // FloatAlertDialog(openDialog = openSetting)

                }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

    }
}