package com.marsanix.ledgo.presentation

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.marsanix.ledgo.data.AppSettingsEntity
import com.marsanix.ledgo.data.DatabaseProvider
import com.marsanix.ledgo.data.TopicEntity
import com.marsanix.ledgo.viewmodel.DigitalEconomyViewModel
import com.marsanix.pelajarankuapp.SideBarNavigationItem
import com.marsanix.ledgo.util.YoutubePlayer
import dev.jeziellago.compose.markdowntext.MarkdownText


@SuppressLint("UseOfNonLambdaOffsetOverload", "UnrememberedMutableState")
@Composable
fun LearningDetailScreen(navController: NavController, id: Int) {

    fun getSelectedItem(id: Int): SideBarNavigationItem? {
        return SideBarNavigationItem.entries.find { it.id == id }
    }


    val selectedNavigationItem by remember { mutableStateOf(getSelectedItem(id)) }
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val databaseProvider = DatabaseProvider(LocalContext.current)
    val materiViewModel = DigitalEconomyViewModel(databaseProvider, Application())
    val materi by materiViewModel.materi.observeAsState()

    LaunchedEffect(id) {
        materiViewModel.getMateri(id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(vertical = 65.dp, horizontal = 16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        selectedNavigationItem?.let {

            if (materi == null) {
                CircularProgressIndicator()

                materiViewModel.insert(
                    TopicEntity(
                        id = it.id,
                        title = it.title,
                        slug = it.slug,
                        description = it.description,
                        content = it.content,
                        youtube = it.youtube,
                        difficulty = 6,
                        moduleCount = 1,
                        isCompleted = true
                    )
                )

                materiViewModel.upsertSetting(
                    AppSettingsEntity(
                        "materi_terakhir_dilihat",
                        it.id.toString()
                    )
                )

//                Toast.makeText(
//                    LocalContext.current,
//                    "Materi telah di tandai",
//                    Toast.LENGTH_SHORT
//                ).show()
            }

            if(it.youtube.isNotEmpty()) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp)
                        .requiredWidth(screenWidth)
                        .zIndex(1f)
                        .graphicsLayer {
                            translationY = scrollState.value.toFloat() // Offset based on scroll
                        }
                ) {

                    YoutubePlayer(
                        youtubeVideoId = it.youtube,
                        lifecycleOwner = LocalLifecycleOwner.current
                    )

                }
            }

            // title
            Text(
                modifier = Modifier.padding(top = 16.dp, bottom = 30.dp),
                text = it.title,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )

            // content body
            MarkdownText(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = 24.dp),
                markdown = it.content,
                textAlign = TextAlign.Unspecified,
            )

        }
    }
}


