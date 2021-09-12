package com.tashariko.exploredb.screen.home.trending

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import com.tashariko.exploredb.R
import com.tashariko.exploredb.screen.home.mainNav.AppBottomNavigation
import com.tashariko.exploredb.screen.home.mainNav.MainScreenNavigationConfig
import com.tashariko.exploredb.theming.appColor
import com.tashariko.exploredb.theming.iconStandardSize
import com.tashariko.exploredb.theming.space18

@Composable
fun TrendingScreen(languageChanged: () -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(title = {
                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    val (titleView, languageButton) = createRefs()
                    Text(context.getString(R.string.toolbar_trending), modifier = Modifier
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
        }
    ) {
        ConstraintLayout(modifier = modifier.fillMaxSize()) {
            val (text) = createRefs()
            Text(
                text = "Trending Screen Screen",
                style = MaterialTheme.typography.h3,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(space18)
                    .constrainAs(text) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        centerVerticallyTo(parent)
                    }
            )
        }
    }
}