package com.tashariko.exploredb.screen.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ConstraintLayout
import com.tashariko.exploredb.R
import com.tashariko.exploredb.application.base.AppCompose
import com.tashariko.exploredb.application.base.BaseActivity
import com.tashariko.exploredb.network.result.ApiResult
import com.tashariko.exploredb.screen.home.MainActivity
import com.tashariko.exploredb.theming.appColor
import com.tashariko.exploredb.theming.progessWidth
import com.tashariko.exploredb.theming.progressSize
import com.tashariko.exploredb.theming.space14
import com.tashariko.exploredb.util.ConfigHelper
import com.tashariko.exploredb.util.UtilityHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingActivity : BaseActivity() {

    /**
     * Bug:
     * Not going fullscreen
     */

    companion object {

        fun launchLandingScreen(context: Context) {
            val mainIntent = Intent(context, LandingActivity::class.java)
            context.startActivity(mainIntent)
            (context as BaseActivity).finish()
        }
    }

    private val splashViewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIncomingIntent()
        setContent {
            AppCompose {
                ScreenContent(viewModel = splashViewModel)
            }
        }

        splashViewModel.getConfig(this)
    }

    override fun handleIncomingIntent() {

    }
}

@Composable
fun ScreenContent(viewModel: SplashViewModel) {

    val viewModelState = viewModel.createTaskLiveData.observeAsState()
    val context = LocalContext.current

    viewModelState.value?.let { state ->

        /**
         * Can use constraint set also
         * https://blog.mindorks.com/constraint-layout-in-jetpack-compose
         */
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (appName, spacer, spacer2, progress, button) = createRefs()
            Text(
                text = LocalContext.current.getString(R.string.app_name),
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(appName) {
                        centerTo(parent)
                    }
            )
            Spacer(modifier = Modifier
                .height(space14)
                .fillMaxWidth()
                .constrainAs(spacer) {
                    top.linkTo(appName.bottom)
                    centerHorizontallyTo(appName)
                })

            when (state.status) {
                ApiResult.Status.LOADING -> {
                    CircularProgressIndicator(color = MaterialTheme.appColor.circularProgressbarColor,
                        strokeWidth = progessWidth,
                        modifier = Modifier
                            .height(progressSize)
                            .width(progressSize)
                            .constrainAs(progress) {
                                top.linkTo(spacer.bottom)
                                centerHorizontallyTo(appName)
                            })
                }
                ApiResult.Status.SUCCESS -> {
                    MainActivity.launchScreen(LocalContext.current)
                    //Toast.makeText(LocalContext.current.applicationContext,"Open main screen",Toast.LENGTH_SHORT).show()
                }

                ApiResult.Status.ERROR -> {
                    Spacer(modifier = Modifier
                        .height(space14)
                        .fillMaxWidth()
                        .constrainAs(spacer2) {
                            bottom.linkTo(parent.bottom)
                        })

                    if(ConfigHelper.isConfigAvailable(context)){
                        MainActivity.launchScreen(LocalContext.current)
                    }else {
                        Button(onClick = { viewModel.getConfig(context = context) },
                            content = { Text(text = LocalContext.current.getString(R.string.retryText)) },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.appColor.primaryLight,
                            ),
                            modifier = Modifier.constrainAs(button) {
                                bottom.linkTo(spacer2.top)
                                centerHorizontallyTo(parent)
                            })
                    }
                }
            }
        }
    }
}