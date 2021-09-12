package com.tashariko.exploredb.screen.home.mainNav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tashariko.exploredb.screen.home.search.SearchScreen
import com.tashariko.exploredb.screen.home.trending.TrendingScreen
import com.tashariko.exploredb.screen.home.user.UserScreen


@Composable
fun MainScreenNavigationConfig(
    languageChanged: () -> Unit, navController: NavHostController, modifier: Modifier
) {
    NavHost(navController, startDestination = BottomNavigationScreens.Trending.route) {
        addNavGraph(languageChanged = languageChanged,modifier = modifier)
    }
}

fun NavGraphBuilder.addNavGraph(
    languageChanged: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable(BottomNavigationScreens.Trending.route) {
        TrendingScreen(languageChanged = languageChanged,modifier = modifier)
    }
    composable(BottomNavigationScreens.Search.route) {
        SearchScreen(modifier = modifier)
    }
    composable(BottomNavigationScreens.User.route) {
        UserScreen(modifier = modifier)
    }
}