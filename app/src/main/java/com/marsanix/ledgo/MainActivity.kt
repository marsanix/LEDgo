package com.marsanix.ledgo

import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.marsanix.ledgo.data.AppSettingsEntity
import com.marsanix.ledgo.data.DatabaseProvider
import com.marsanix.ledgo.data.TopicDao
import com.marsanix.ledgo.data.UserProgressDao
import com.marsanix.ledgo.data.UserProgressEntity
import com.marsanix.ledgo.navigation.AppRoute
import com.marsanix.ledgo.navigation.CustomDrawer
import com.marsanix.ledgo.navigation.CustomDrawerState
import com.marsanix.ledgo.navigation.isOpened
import com.marsanix.ledgo.navigation.opposite
import com.marsanix.ledgo.presentation.HomeScreen
import com.marsanix.ledgo.presentation.LearningModuleScreen
import com.marsanix.ledgo.presentation.LearningDetailScreen
import com.marsanix.ledgo.presentation.LearningScreen
import com.marsanix.ledgo.presentation.ProfileScreen
import com.marsanix.ledgo.ui.theme.LEDgoTheme
import com.marsanix.ledgo.viewmodel.DigitalEconomyViewModel
import com.marsanix.ledgo.viewmodel.coloredShadow
import com.marsanix.pelajarankuapp.SideBarNavigationItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

const val APP_NAME: String = "LEDgo"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            LEDgoTheme {
                val navController: NavHostController = rememberNavController()
                Surface(
                    color = MaterialTheme.colorScheme.background) {
                    Greeting(navController)
                }
            }
        }
    }
}

@Composable
fun Greeting(navController: NavHostController = rememberNavController()) {
    EconomyDigitalApp(navController)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LEDgoTheme {
        Greeting()
    }
}

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun EconomyDigitalApp(navController: NavHostController = rememberNavController()) {

    /* Start for SideBar */
    var drawerState by remember { mutableStateOf(CustomDrawerState.Closed) }
    var selectedNavigationItem by remember { mutableStateOf(SideBarNavigationItem.Pengantar) }

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density

    val screenWidth = remember {
        derivedStateOf { (configuration.screenWidthDp * density).roundToInt() }
    }
    val offsetValue by remember { derivedStateOf { (screenWidth.value / 4.2).dp } }
    val animatedOffset by animateDpAsState(
        targetValue = if (drawerState.isOpened()) offsetValue else 0.dp,
        label = "Animated Offset"
    )
    val animatedScale by animateFloatAsState(
        targetValue = if (drawerState.isOpened()) 0.9f else 1f,
        label = "Animated Scale"
    )

    BackHandler(enabled = drawerState.isOpened()) {
        drawerState = CustomDrawerState.Closed
    }

    /* End for SideBar */

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
    ) {
        CustomDrawer(
            selectedNavigationItem = selectedNavigationItem,
            onNavigationItemClick = {
                selectedNavigationItem = it

                navController.navigate("detail/${it.id}")
                drawerState = CustomDrawerState.Closed

            },
            onCloseClick = { drawerState = CustomDrawerState.Closed }
        )

        MainContent(
            modifier = Modifier
                .offset(x = animatedOffset)
                .scale(scale = animatedScale)
                .coloredShadow(
                    color = Color.Black,
                    alpha = 0.1f,
                    shadowRadius = 50.dp
                ),
            drawerState = drawerState,
            onDrawerClick = { drawerState = it },
            navController
        )
    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDateTime(): String {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    return current.format(formatter)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    drawerState: CustomDrawerState,
    onDrawerClick: (CustomDrawerState) -> Unit,
    navController: NavHostController = rememberNavController(),
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val databaseProvider = DatabaseProvider(LocalContext.current)
    val viewModel = DigitalEconomyViewModel(databaseProvider, Application())

    Log.e("_current.format(formatter)", getCurrentDateTime())

    viewModel.upsertSetting(
        AppSettingsEntity(
            "aktivitas_terakhir",
            getCurrentDateTime()
        )
    )

    Scaffold(
        modifier = modifier
            .clickable(enabled = drawerState == CustomDrawerState.Opened) {
                onDrawerClick(CustomDrawerState.Closed)
            },
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = { Text(text = APP_NAME, color = Color.DarkGray) },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Transparent // Set the background color to the primary theme color
                ),
                navigationIcon = {
                    IconButton(onClick = { onDrawerClick(drawerState.opposite()) }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu Icon",
                            tint = Color.DarkGray
                        )
                    }
                }
            )
        },
        floatingActionButton = {

            FloatingActionButton(
                modifier = Modifier
                    .scale(1.1f, 1.1f)
                    .offset(0.dp, 35.dp),
                onClick = {
                    navController.navigate("learning")
                },
                containerColor = if (currentRoute == "learning") Color.DarkGray else colorResource(R.color.fab_color),
                shape = CircleShape,
            ) {
                Icon(painterResource(R.drawable.baseline_menu_book_24), tint = Color.White, contentDescription = "")
            }

        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            BottomAppBar(
                cutoutShape = RoundedCornerShape(50),
                backgroundColor = colorResource(R.color.primary_color),
                content = {
                    BottomNavigation(
                        backgroundColor = colorResource(R.color.primary_color)
                    ) {
                        BottomNavigationItem(

                            selected = currentRoute == "home",
                            onClick = {
                                navController.navigate("home")
                            },
                            icon = {
                                Icon(
                                    Icons.Filled.Home,
                                    contentDescription = "home",
                                    tint = Color.White
                                )
                            },
                            label = { Text(text = "Home", color = Color.White) },
                            selectedContentColor = Color.White,
                            alwaysShowLabel = false,
                        )

                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .scale(1.6f, 1.6f)
                                        .offset(0.dp, (-21).dp)
                                    ,
                                    imageVector =  Icons.Filled.AddCircle,
                                    contentDescription = "",
                                    tint = Color.White
                                )
                            },
                            selected = currentRoute == "learning",
                            onClick = {

                                // content.value = "Halaman Materi"
                                // selectedItem.value = "materi"
                            }

                        )

                        BottomNavigationItem(
                            selected = currentRoute == "profile",
                            onClick = {
                                navController.navigate("profile")
                            },
                            icon = {
                                Icon(
                                    Icons.Filled.Person,
                                    contentDescription = "profile",
                                    tint = Color.White
                                )
                            },
                            label = { Text(text = "Profile", color = Color.White) },
                            selectedContentColor = Color.White,
                            alwaysShowLabel = false
                        )
                    }
                }
            )
        },
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            NavHost(
                navController = navController,
                startDestination = AppRoute.Home.route,
            ) {

                composable(AppRoute.Home.route) {
                    HomeScreen(navController)
                }

                composable(AppRoute.Learning.route) {
                    LearningScreen(navController)
                }

                composable(AppRoute.ArticleDetail.route + "/{id}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id")
                    if (id != null) {
                        LearningDetailScreen(navController, id.toInt())
                    }
                }

                composable(AppRoute.Profile.route) {
                    ProfileScreen(navController)
                }

            }

        }
    }
}

