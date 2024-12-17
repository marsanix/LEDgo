package com.marsanix.ledgo.presentation

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
// import coil.compose.rememberAsyncImagePainter
import com.marsanix.ledgo.R
import com.marsanix.ledgo.data.AppSettingsEntity
import com.marsanix.ledgo.data.DatabaseProvider
import com.marsanix.ledgo.data.SharedPreferencesManager
import com.marsanix.ledgo.data.TopicEntity
import com.marsanix.ledgo.data.UserProgressEntity
import com.marsanix.ledgo.getCurrentDateTime
import com.marsanix.ledgo.viewmodel.DigitalEconomyViewModel
import com.marsanix.ledgo.viewmodel.getNextItem
import com.marsanix.ledgo.viewmodel.getSelectedItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Komponen Utama Layar Beranda
@SuppressLint("NewApi")
@Composable
fun HomeScreen(
    navController: NavController,
) {
    val databaseProvider = DatabaseProvider(LocalContext.current)
    val viewModel = DigitalEconomyViewModel(databaseProvider, Application())

    val topics by viewModel.topics.observeAsState(emptyList())
    val materiSetting by  viewModel.materiSetting.observeAsState()
    val lastActivity by viewModel.lastActivity.observeAsState()

    LaunchedEffect(topics) {
        viewModel.loadTopics()
    }

    LaunchedEffect(materiSetting) {
        viewModel.getMateriSetting("materi_terakhir_dilihat")
    }

    LaunchedEffect(getCurrentDateTime()) {
        viewModel.getLastActivity()
    }

    Column(modifier = Modifier
        .background(Color.White)
        .fillMaxSize()
        .padding(top = 70.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val images = listOf(
            R.drawable.slide2,
            R.drawable.slide1,
            R.drawable.slide3
        )

        HomeContent(images, materiSetting, lastActivity)

    }

}

@Composable
fun HomeContent(images: List<Any>, materiSetting: AppSettingsEntity?, materiSettingLastLogin: AppSettingsEntity?) {
    var currentImageIndex by remember { mutableIntStateOf(0) }
    var isAnimating by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val sharedPreferencesManager = remember {
        SharedPreferencesManager(context)
    }

    var yourName by remember { mutableStateOf(sharedPreferencesManager.userName) }
    LaunchedEffect(sharedPreferencesManager.userName) {
        yourName = sharedPreferencesManager.userName
    }

    LaunchedEffect(currentImageIndex) {
        while (true) {
            delay(5000L)
            if (!isAnimating) {
                val nextIndex = (currentImageIndex + 1) % images.size
                currentImageIndex = nextIndex
            }
        }

    }

    Column(modifier = Modifier.fillMaxSize()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp, 16.dp)
            .align(Alignment.CenterHorizontally)
        ) {
            // Scrollable Row of Cards
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(images) { index, image ->
                    Card(
                        modifier = Modifier
                            .width(325.dp)
                            .height(200.dp)
                            .clickable {
                                if (index != currentImageIndex && !isAnimating) {
                                    isAnimating = true
                                    coroutineScope.launch {
                                        val delayMillis = 500L
                                        // Wait for the card to change color before animating
                                        delay(delayMillis / 2)
                                        currentImageIndex = index
                                        delay(delayMillis)
                                        isAnimating = false
                                    }
                                }
                            },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = image),
                            contentDescription = "My Image",
                            modifier = Modifier
                                .fillMaxSize()
                                .width(300.dp)
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )

                    }
                }

            }

        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .graphicsLayer {
                    shadowElevation = 2f
                    shape = RoundedCornerShape(16.dp)
                    clip = true
                }
                .drawWithContent {
                    drawContent()
                    drawCircle(
                        color = Color.LightGray.copy(alpha = 0.25f),
                        radius = 420f,
                        center = Offset(size.width - 50, size.height -480 )
                    )

                }
            ,
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF673AB7))
        ) {
            Row {

                Icon(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 0.dp, bottom = 16.dp),
                    painter = painterResource(R.drawable.baseline_person_24),
                    contentDescription = "",
                    tint = Color.White
                )

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                    ,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Selamat Datang, ${yourName}!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Aktivitas terakhir: ${materiSettingLastLogin?.value}",
                        fontSize = 14.sp,
                        color = Color(0xFFDCDCDC)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .graphicsLayer {
                        shadowElevation = 2f
                        shape = RoundedCornerShape(16.dp)
                        clip = true
                    }
                    .drawWithContent {
                        drawContent()
                        drawCircle(
                            color = Color.LightGray.copy(alpha = 0.25f),
                            radius = 450f,
                            center = Offset(size.width +90, size.height -600 )
                        )

                    }

                ,
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF03A9F4))

            ) {

                Box(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 55.dp)
                        .wrapContentSize(),
                ) {

                    Row {
                        Icon(
                            modifier = Modifier.padding(end = 4.dp),
                            painter = painterResource(R.drawable.baseline_menu_book_24),
                            contentDescription = "",
                            tint = Color.White
                        )
                        Text("Materi selanjutnya:", fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(0.dp, 45.dp)
                            .padding(4.dp)
                    ) {

                        materiSetting?.let {
                            val materiSelanjutnya = getNextItem(it.value)
                            val materiTerakhir = getSelectedItem(it.value)
                            if(materiSelanjutnya == materiTerakhir) {
                                Text(text = "Terima kasih! Anda sudah menyelesaikan seluruh materi.", color = Color.White)
                            } else {
                                Text(text = materiSelanjutnya.title, color = Color.White)
                            }
                        } ?: run {
                            Text(text ="Silahkan masuk ke halaman Materi!", color = Color.White)
                        }

                    }

                }

            }

            Spacer(modifier = Modifier.width(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .graphicsLayer {
                        shadowElevation = 2f
                        shape = RoundedCornerShape(16.dp)
                        clip = true
                    }
                    .drawWithContent {
                        drawContent()
                        drawCircle(
                            color = Color.LightGray.copy(alpha = 0.25f),
                            radius = 450f,
                            center = Offset(size.width +90, size.height -600 )
                        )

                    }
                ,
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50))
            ) {

                Box(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 55.dp)
                        .wrapContentSize()

                    ,
                ) {

                    Row {
                        Icon(
                            modifier = Modifier.padding(end = 4.dp),
                            painter = painterResource(R.drawable.baseline_history_24),
                            contentDescription = "",
                            tint = Color.White
                        )
                        Text("Materi terlahir dilihat:", fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(0.dp, 45.dp)
                            .padding(4.dp)
                    ) {

                        materiSetting?.let {
                            val materiTerakhir = getSelectedItem(it.value)
                            if (materiTerakhir != null) {
                                Text(text = materiTerakhir.title, color = Color.White)
                            } else {
                                Text(text ="Belum ada materi yang di buka.", color = Color.White)
                            }
                        } ?: run {
                            Text(text ="Belum ada materi yang di buka.", color = Color.White)
                        }

                    }

                }

            }
        }

    }
}

