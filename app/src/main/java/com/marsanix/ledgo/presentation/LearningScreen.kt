package com.marsanix.ledgo.presentation

import android.app.Application
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.marsanix.ledgo.data.DatabaseProvider
import com.marsanix.ledgo.viewmodel.DigitalEconomyViewModel
import com.marsanix.pelajarankuapp.SideBarNavigationItem

@Composable
fun LearningScreen(navController: NavController) {

    Column(modifier = Modifier
        .background(Color.White)
        .fillMaxSize()
        .padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val databaseProvider = DatabaseProvider(LocalContext.current)
        val materiViewModel = DigitalEconomyViewModel(databaseProvider, Application())

        val filteredList = SideBarNavigationItem.entries.subList(0, SideBarNavigationItem.entries.size - 1)
        val materiList by materiViewModel.topics.observeAsState(emptyList())
        //val progressLists by materiViewModel.userProgress.observeAsState(emptyMap())
        var idx by remember { mutableIntStateOf(1) }

        LaunchedEffect(filteredList) {
            materiViewModel.loadTopics()
        }

//        LaunchedEffect(materiList) {
//            materiViewModel.loadUserProgress()
//        }

        LazyColumn(modifier = Modifier.padding(bottom = 50.dp)) {

            idx = 1
            items(filteredList) { item ->

                var isOpened by remember { mutableStateOf(false) }
                // var idFromDb by remember { mutableStateOf(null) }
                LaunchedEffect(materiList) {
                    materiList.forEach{ materiItem ->

                        // Log.e("_progressLists", progressLists.toString())

                        Log.e("IS_CHECKED", materiItem.id.toString() + " - " + item.id.toString() + " - " + materiItem.isCompleted)

                        if(materiItem.id.toString() == item.id.toString() && materiItem.isCompleted) {

                            // idFromDb = materiItem.id
                            isOpened = true
                        }
                    }
                }

                MyListItem(
                    selectedId = item.id,
                    icon = painterResource(item.icon),
                    title = item.title,
                    subtitle = item.description,
                    isOpened = isOpened,
                    onClick = {
                        navController.navigate("detail/${item.id}")
                    }
                )

                idx++
            }
        }


    }
}

@Composable
fun MyListItem(
    selectedId: Int,
    icon: Painter,
    title: String,
    subtitle: String,
    isOpened: Boolean,
    onClick: () -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            if(isOpened) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = "",
                    modifier = Modifier.align(Alignment.TopEnd)
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }

            // Log.e("__materiList__", idx.toString() + ". " + materiItem.slug)

            // Text(text = slufromdb)

            val bgColor = if(isOpened) Color(0xFF03A9F4).copy(alpha = 0.1f) else Color(0xFF4CAF50).copy(alpha = 0.1f)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(bgColor)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = title, style = TextStyle(fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = subtitle, style = TextStyle(fontSize = 14.sp))
                }
            }

        }


    }
}