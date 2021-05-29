package com.tashariko.exploredb.screen.mainBottomTabs

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FeaturedVideo
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tashariko.exploredb.R


sealed class BottomNavigationScreens(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Trending : BottomNavigationScreens("RouteTrending", R.string.toolbar_trending, Icons.Filled.FeaturedVideo)
    object Search : BottomNavigationScreens("RouteSearch", R.string.toolbar_search, Icons.Filled.Search)
    object User : BottomNavigationScreens("RouteUser", R.string.toolbar_user, Icons.Filled.Person)
}

@Composable
fun MainScreenContent() {
    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Trending,
        BottomNavigationScreens.Search,
        BottomNavigationScreens.User
    )

    Scaffold(
        topBar = {  },
        bottomBar = {
            AppBottomNavigation(navController, bottomNavigationItems)
        },
    ) {
        MainScreenNavigationConfigurations(navController)
    }
}



@Composable
private fun AppBottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens>
) {
    BottomNavigation {
        val currentRoute = currentRoute(navController)

        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Image(screen.icon,screen.route) },
                label = { Text(stringResource(id = screen.resourceId)) },
                selected = currentRoute == screen.route,
                alwaysShowLabel = false,
                onClick = {
                    // This if check gives us a "singleTop" behavior where we do not create a
                    // second instance of the composable if we are already on that destination
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}

@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController
) {
    NavHost(navController, startDestination = BottomNavigationScreens.Trending.route) {
        composable(BottomNavigationScreens.Trending.route) {
            MainTabScreen(BottomNavigationScreens.Trending.route)
        }
        composable(BottomNavigationScreens.Search.route) {
            MainTabScreen(BottomNavigationScreens.Search.route)
        }
        composable(BottomNavigationScreens.User.route) {
            MainTabScreen(BottomNavigationScreens.User.route)
        }
    }
}

@Composable
fun MainTabScreen(route: String) {
    Text(text = route)
}

@Composable
private fun currentRoute(navController: NavHostController): String {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    return navBackStackEntry.value?.destination?.route?.let{
        it
    }?: run {
        ""
    }
}