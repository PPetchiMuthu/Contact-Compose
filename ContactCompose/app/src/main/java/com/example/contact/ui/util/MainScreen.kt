package com.example.contact.ui.util

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.contact.R
import com.example.contact.ui.add_edit_screen.components.AddScreen
import com.example.contact.ui.all_contact_screen.components.AllContactScreen
import com.example.contact.ui.all_contact_screen.components.ContactViewModel
import com.example.contact.ui.favorite_contact.FavoriteScreen
import com.example.contact.ui.util.BottomBarScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {
        BottomNavGraph(navController = navController)
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Favorite
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected =
        currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true
    NavigationBarItem(
        selected = selected,
        onClick = {
            navController.navigate(screen.route)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = null
            )
        },
        label = { Text(text = screen.title) }
    )
}

@Composable
fun BottomNavGraph(navController: NavHostController) {
    val viewModel: ContactViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            AllContactScreen(viewModel, navController)
        }
        composable(route = BottomBarScreen.Favorite.route) {
            FavoriteScreen(viewModel)
        }
        composable(route = BottomBarScreen.AddEdit.route + "?contactId={contactId}",
            arguments = listOf(
                navArgument(
                    name = "contactId"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            AddScreen(navController)
        }
    }
}

@Composable
fun DefaultImage(size:Int) {
    Image(
        imageVector = ImageVector.vectorResource(id = R.drawable.default_image),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
    )
}