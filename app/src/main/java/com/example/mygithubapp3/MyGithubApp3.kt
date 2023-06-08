package com.example.mygithubapp3

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mygithubapp3.ui.navigation.Screen
import com.example.mygithubapp3.ui.screen.about.AboutScreen
import com.example.mygithubapp3.ui.screen.detail.DetailScreen
import com.example.mygithubapp3.ui.screen.favorite.FavoriteScreen
import com.example.mygithubapp3.ui.screen.home.HomeScreen
import com.example.mygithubapp3.ui.theme.MyGithubApp3Theme

@Composable
fun MyGithubApp3(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (Screen.navigationItems.any { it.screen.route == currentRoute })
                BottomBar(
                    navController = navController,
                    modifier = modifier
                )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    modifier = modifier
                ) { username ->
                    navController.navigate(Screen.Detail.createRoute(username))
                }
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    modifier = modifier
                ) { username ->
                    navController.navigate(Screen.Detail.createRoute(username))
                }
            }
            composable(Screen.About.route) {
                AboutScreen(
                    modifier = modifier.semantics { contentDescription = "about_page" }
                ) { username ->
                    navController.navigate(Screen.Detail.createRoute(username))
                }
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("username") { type = NavType.StringType })
            ) {
                val username = it.arguments?.getString("username") ?: ""
                DetailScreen(
                    modifier = modifier,
                    username = username
                ) { clickedUsername ->
                    navController.navigate(Screen.Detail.createRoute(clickedUsername))
                }
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar(
        modifier = modifier,
    ) {
        Screen.navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(item.title)
                    )
                },
                label = { Text(stringResource(item.title)) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}

//------------------------------------------[ preview ]---------------------------------------------

@Preview(showBackground = true)
@Composable
fun MyGithubApp3Preview() {
    MyGithubApp3Theme {
        MyGithubApp3()
    }
}

