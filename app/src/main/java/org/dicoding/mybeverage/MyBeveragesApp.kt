package org.dicoding.mybeverage

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.dicoding.mybeverage.ui.navigation.NavigationItem
import org.dicoding.mybeverage.ui.navigation.Screen
import org.dicoding.mybeverage.ui.screen.detail.DetailScreen
import org.dicoding.mybeverage.ui.screen.home.HomeScreen
import org.dicoding.mybeverage.ui.screen.profile.ProfileScreen
import org.dicoding.mybeverage.ui.screen.splash.SplashScreen
import org.dicoding.mybeverage.ui.theme.MyBeverageTheme

@Composable
fun MyBeveragesApp (
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if(currentRoute == Screen.Home.route){
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.app_name))
                    }
                )
            }else if(currentRoute == Screen.Profile.route){
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.menu_profile))
                    }
                )
            }
        },
        bottomBar = {
            if (currentRoute != Screen.Detail.route && currentRoute != Screen.Splash.route) {
                BottomBar(navController = navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(Screen.Splash.route){
                SplashScreen(navController = navController)
            }
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { id ->
                        navController.navigate(Screen.Detail.createRoute(id))
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType }),
            ) {
                val id = it.arguments?.getInt("id") ?: -1L
                DetailScreen(
                    id = id as Int,
                    navigateBack = {
                        navController.navigateUp()
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyBeveragesAppPreview() {
    MyBeverageTheme {
        MyBeveragesApp()
    }
}

@Composable
private fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    BottomNavigation(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                contentDescription = "home_page",
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                contentDescription = "about_page",
                screen = Screen.Profile
            ),
        )
        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.contentDescription
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

