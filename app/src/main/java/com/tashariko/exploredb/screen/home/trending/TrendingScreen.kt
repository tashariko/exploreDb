package com.tashariko.exploredb.screen.home.trending

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.tashariko.exploredb.R
import com.tashariko.exploredb.database.entity.TrendingItem
import com.tashariko.exploredb.screen.home.trending.ui.TrendingViewModel
import com.tashariko.exploredb.theming.appColor
import com.tashariko.exploredb.theming.iconStandardSize
import com.tashariko.exploredb.theming.space14
import com.tashariko.exploredb.theming.space18


@ExperimentalPagingApi
@Composable
fun TrendingScreen(languageChanged: () -> Unit, viewModel: TrendingViewModel, modifier: Modifier = Modifier) {

    val context = LocalContext.current

    /**
     * Continue with pagination
     * https://blog.mindorks.com/pagination-in-jetpack-compose
     */
    val trendingList: LazyPagingItems<TrendingItem> = viewModel.fetchTrendingList().collectAsLazyPagingItems()

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
        ConstraintLayout(modifier = modifier
            .fillMaxSize()
            .padding(space18)) {
            val (text) = createRefs()
            LazyColumn(modifier = modifier
                .fillMaxSize()
                .constrainAs(text) {
                    start.linkTo(parent.start)
                }
            ) {
                items(trendingList) { item ->
                    Text(text = "Item: $item")
                    Spacer(modifier = Modifier.height(height = space18))
                    Spacer(modifier = Modifier.height(height = space18))
                }

                trendingList.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> item {
                            showProgress()
                        }
                        loadState.append is LoadState.Loading ->  item {
                            showProgress()
                        }
                    }
                }

                trendingList.apply {
                    when {
                        loadState.refresh is LoadState.Error -> item {
                            showError(trendingList)
                        }
                        loadState.append is LoadState.Error ->  item {
                            showError(trendingList)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun showProgress() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(height = space14))
        Box(modifier = Modifier.fillMaxSize() ) {
            CircularProgressIndicator(
                color = MaterialTheme.appColor.circularProgressbarColor,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}


@Composable
fun showError(trendingList: LazyPagingItems<TrendingItem>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(height = space14))
        Button(onClick = {
                         trendingList.retry()
        },
            content = { Text(text = LocalContext.current.getString(R.string.retryText)) },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.appColor.primaryLight,
            ))
    }

}