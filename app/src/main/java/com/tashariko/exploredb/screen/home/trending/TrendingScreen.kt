package com.tashariko.exploredb.screen.home.trending

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
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
                    item?.let {
                        getItemView(it,viewModel, context)
                    }
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

/**
 * For coil in compose: https://coil-kt.github.io/coil/compose/
 */
@Composable
private fun getItemView(item: TrendingItem, viewModel: TrendingViewModel, context: Context) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item.posterPath?.let {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(color = MaterialTheme.colors.background)
            ) {
                Image(
                    painter = rememberImagePainter(
                        "${viewModel.getImagePath(context).first}${
                            viewModel.getImagePath(
                                context
                            ).third
                        }${it}"
                    ),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(180.dp),
                )
            }
        } ?: run {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(color = MaterialTheme.colors.background)
            ) {
                Image(
                    painterResource(R.drawable.icon_movie),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(180.dp),
                )
            }
        }

        Text(
            text = item.originalTitle ?: "No Title",
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(0.dp,2.dp,0.dp,0.dp)
        )

        Text(
            text = item.overview ?: "No Overview",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(0.dp,2.dp,0.dp,0.dp)
        )

        Text(
            text = item.mediaType,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(0.dp,2.dp,0.dp, space14)
        )
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