package com.marsanix.ledgo.presentation

import android.app.Application
import android.opengl.Visibility
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.marsanix.ledgo.data.DatabaseProvider
import com.marsanix.ledgo.viewmodel.DigitalEconomyViewModel
import java.lang.Boolean

// Layar Detail Pembelajaran
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningModuleScreen(
    navController: NavController,
    topicId: Int,
) {

    val databaseProvider = DatabaseProvider(LocalContext.current)
    val viewModel = DigitalEconomyViewModel(databaseProvider, Application())

    var isCompleted by remember { mutableStateOf(false) }

    val topics by viewModel.topics.observeAsState(emptyList())
    val topic by remember {
        derivedStateOf {
            topics.find {
                it.id == topicId
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(topic?.title ?: "Modul Pembelajaran") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Implementasi detail modul pembelajaran

        val scrollState = rememberScrollState()

        BoxWithConstraints(
            modifier = Modifier.padding(16.dp)
        ) {
            val screenHeight = maxHeight

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
            ) {


                        // Konten modul
                        Text(
                            text = (topic?.description + """
                                ---------------------------- PROCESS STARTED (23519) for package com.marsanix.ledgo ----------------------------
                                2024-12-15 11:43:43.490 23519-23519 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:43:43.543 23519-23519 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:43:44.150 23519-23519 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:43:44.194 23519-23519 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:44:05.185 23519-23519 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:44:09.233 23519-23519 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:44:13.427 23519-23519 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:44:16.924 23519-23519 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:44:37.518 23519-23519 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:44:40.834 23519-23519 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:46:31.082 23519-23519 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:46:33.872 23519-23519 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                ---------------------------- PROCESS ENDED (23519) for package com.marsanix.ledgo ----------------------------
                                ---------------------------- PROCESS STARTED (23904) for package com.marsanix.ledgo ----------------------------
                                2024-12-15 11:46:45.317 23904-23904 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:46:45.387 23904-23904 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:46:45.992 23904-23904 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:46:46.094 23904-23904 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                ---------------------------- PROCESS ENDED (23904) for package com.marsanix.ledgo ----------------------------
                                ---------------------------- PROCESS STARTED (24195) for package com.marsanix.ledgo ----------------------------
                                2024-12-15 11:47:33.909 24195-24195 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:47:33.977 24195-24195 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:47:34.581 24195-24195 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:47:34.631 24195-24195 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:47:48.700 24195-24195 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:47:48.765 24195-24195 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:47:49.351 24195-24195 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:47:49.435 24195-24195 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:47:52.151 24195-24195 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                2024-12-15 11:47:52.697 24195-24195 screenHeight            com.marsanix.ledgo                   E  820.0.dp
                                
                            """.trimIndent()) ?: "Tidak ada deskripsi"
                            ,
                            style = MaterialTheme.typography.bodyLarge
                        )


                        // Daftar sub-modul atau materi
                        // Implementasi detail bisa ditambahkan di sini

                        AnimatedVisibility(visible = isCompleted) {
                            OutlinedButton(
                                modifier = Modifier.background(Color.Transparent).fillMaxWidth(),
                                border = BorderStroke(1.dp, Color.Gray),
                                onClick = {
                                    viewModel.updateTopicProgress(topicId, 100, 0)
                                }) {
                                Text("Completed!")
                            }
                        }



            }

            LaunchedEffect(key1 = scrollState) {
                snapshotFlow { scrollState.value }
                    .collect { scrollPosition ->
                        // Check if scroll position is near the bottom of the screen
                        if (scrollPosition + screenHeight.value >= scrollState.maxValue) {
                            viewModel.updateTopicProgress(topicId, 100, 0) // Trigger loading more items
                            isCompleted = true
                        }
                    }
            }

        }



    }
}