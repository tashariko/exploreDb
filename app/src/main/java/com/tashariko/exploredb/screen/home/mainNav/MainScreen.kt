package com.tashariko.exploredb.screen.home.mainNav

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.compose.rememberNavController
import com.tashariko.exploredb.R
import com.tashariko.exploredb.theming.appColor
import com.tashariko.exploredb.theming.iconStandardSize

@Composable
fun MainScreenContent(languageChanged: () -> Unit) {
    val context = LocalContext.current
    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Trending,
        BottomNavigationScreens.Search,
        BottomNavigationScreens.User
    )

    Scaffold(
        topBar = {
            TopAppBar(title = {
                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    val (titleView, languageButton) = createRefs()
                    Text(context.getString(R.string.fragment_trending_title), modifier = Modifier
                        .constrainAs(titleView) {
                            centerVerticallyTo(parent)
                            start.linkTo(parent.start)
                        })
                    val image: Painter = painterResource(id = R.drawable.icon_language_change)
                    Image(painter = image, contentDescription = "", contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxHeight(0.8f)
                            .width(iconStandardSize)
                            .constrainAs(languageButton) {
                                centerVerticallyTo(parent)
                                end.linkTo(parent.end)
                            }
                            .clickable(onClick = languageChanged))
                }
            }, backgroundColor = MaterialTheme.appColor.material.primary)
        },
        bottomBar = {
            AppBottomNavigation(navController, bottomNavigationItems)
        },
    ) { innerPadding ->
        MainScreenNavigationConfig(navController, modifier = Modifier.padding(innerPadding))
    }
}