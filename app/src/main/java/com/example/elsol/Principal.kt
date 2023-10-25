package com.example.elsol

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.elsol.ui.theme.Pink40
import kotlinx.coroutines.launch
import java.security.Principal



@Composable
fun SolVerticalGrid() {
    val context = LocalContext.current
    val solList = remember { getSol() }
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(solList) { solInfo ->
            itemSol(solInfo = solInfo) {
                Toast.makeText(context, solInfo.name, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

fun getSol(): List<SolInfo> {
    return listOf(
        SolInfo(
            "Corona Solar",
            R.drawable.corona_solar
        ),
        SolInfo(
            "Erupcion Solar",
            R.drawable.erupcionsolar
        ),
        SolInfo(
            "Espiculas",
            R.drawable.espiculas
        ),
        SolInfo(
            "Filamentos",
            R.drawable.filamentos
        ),
        SolInfo(
            "Magnetosfera",
            R.drawable.magnetosfera
        ),
        SolInfo(
            "Mancha Solar",
            R.drawable.manchasolar
        ),
    )
}
@Composable
fun itemSol(solInfo: SolInfo, navController: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Card (
        modifier = Modifier
            .padding(top = 5.dp)
            .padding(horizontal = 5.dp)
            .clickable {},
        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Column {
            Image(
                painter = painterResource(id = solInfo.image),
                contentDescription = "Coffee Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier
                .size(10.dp)
            )
            Row {
                Text(
                    text = solInfo.name,
                    modifier = Modifier
                        .weight(3f)
                        .padding(5.dp),
                    fontSize = 18.sp
                )
                IconButton(onClick = {expanded = !expanded}) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        modifier = Modifier.weight(1f),
                        contentDescription = "Buscar", tint = Color.Black,
                    )
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = !expanded}) {
                    DropdownMenuItem(text = { Text(text = "Compartir") },
                        onClick = { /*TODO*/ }, leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Share, contentDescription = "Compartir"
                            )
                        })
                    DropdownMenuItem(text = { Text(text = "Album") },
                        onClick = { /*TODO*/ }, leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Lock, contentDescription = "Album"
                            )
                        })
                }

            }
            Spacer(modifier = Modifier
                .size(5.dp)
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyModalDrawer(navControler: NavHostController,drawerState: DrawerState) {
    ModalNavigationDrawer(drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Drawer title", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Build") },
                    selected = false,
                    onClick = { navControler.navigate("Build") }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Info") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Email") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
            }
        }
    ) {
    }
}