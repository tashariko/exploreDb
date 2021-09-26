package com.tashariko.exploredb.screen.home.mainNav

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FeaturedVideo
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tashariko.exploredb.R
import com.tashariko.exploredb.application.NavRouteConstants

sealed class BottomNavigationScreens(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    object Trending : BottomNavigationScreens(
        NavRouteConstants.ROUTE_HOME_TRENDING,
        R.string.toolbar_trending,
        Icons.Filled.FeaturedVideo
    )

    object Search : BottomNavigationScreens(
        NavRouteConstants.ROUTE_HOME_SEARCH,
        R.string.toolbar_search,
        Icons.Filled.Search
    )

    object User : BottomNavigationScreens(
        NavRouteConstants.ROUTE_HOME_USER,
        R.string.toolbar_user,
        Icons.Filled.Person
    )
}

@Composable
fun AppBottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens>
) {
    BottomNavigation {
        val currentRoute = currentRoute(navController)

        items.forEach { screen ->

            /**
             * To customize bottom bar, can refer to @JetsnackBottomNavLayout in:
             * https://github.com/android/compose-samples/blob/main/Jetsnack/app/src/main/java/com/example/jetsnack/ui/home/Home.kt
             */
            BottomNavigationItem(
                icon = { Image(screen.icon, screen.route) },
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
private fun currentRoute(navController: NavHostController): String {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    return navBackStackEntry.value?.destination?.route?.let {
        it
    } ?: run {
        ""
    }
}