package com.example.elsol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.elsol.ui.theme.ElSolTheme
import kotlinx.coroutines.launch
import java.security.Principal

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElSolTheme {
                val navController = rememberNavController()
                var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    bottomBar = {
                        BottomAppBar(drawerState = drawerState)
                        SnackbarHost(hostState = snackbarHostState)
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = it.calculateBottomPadding())
                    ) {
                        NavHost(navController = navController, startDestination = "Principal") {
                            composable("Principal") { Principal(navController, snackbarHostState = SnackbarHostState()) }
                            composable("Filled.Email") { Email(navController, snackbarHostState =  SnackbarHostState()) }
                            composable("Filled.Info") { Info(navController, snackbarHostState =  SnackbarHostState()) }
                            composable("Filled.Build") { Principal(navController, snackbarHostState =  SnackbarHostState()) }

                        }
                        val items = listOf(Icons.Default.Build, Icons.Default.Info, Icons.Default.Email)
                        val selectedItem = remember {
                            mutableStateOf(items[0])
                        }
                        ModalNavigationDrawer(drawerState = drawerState,
                            drawerContent = {
                                ModalDrawerSheet {
                                    Image(
                                        painter = painterResource(id = R.drawable.corona_solar),
                                        contentDescription = "Image",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(220.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    items.forEach { item ->
                                        NavigationDrawerItem(
                                            icon = { Icon(item, contentDescription = null) },
                                            label = { Text(text = item.name.substringAfter(".")) },
                                            selected = item == selectedItem.value,
                                            onClick = {
                                                scope.launch { drawerState.close() }
                                                selectedItem.value = item
                                                navController.navigate(item.name)
                                            })
                                    }
                                }
                            }, content = {
                                SolVerticalGrid()
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(bottom = it.calculateBottomPadding())
                                ) {
                                }
                            })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun BottomAppBar(drawerState: DrawerState) {
    var expanded by remember { mutableStateOf(false) }
    var badgeCount by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    androidx.compose.material3.BottomAppBar(containerColor = Color.Red) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            scope.launch { drawerState.open() }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                    BadgedBox(badge = {
                        Badge {
                            Text(text = badgeCount.toString())
                        }

                    }, modifier = Modifier
                        .padding(10.dp)
                        .clickable { badgeCount++ }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }
                Row {
                    FloatingActionButton(onClick = { /*TODO*/ }, containerColor = Color.Black) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

class SolInfo (
    val name: String,
    val image: Int,
)