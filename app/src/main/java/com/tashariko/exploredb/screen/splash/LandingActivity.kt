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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.tashariko.exploredb.R
import com.tashariko.exploredb.application.base.AppCompose
import com.tashariko.exploredb.application.base.BaseActivity
import com.tashariko.exploredb.network.result.ApiResult
import com.tashariko.exploredb.network.result.ErrorType
import com.tashariko.exploredb.screen.main.MainActivity
import com.tashariko.exploredb.screen.main.movieDetail.MovieDetailViewModel
import com.tashariko.exploredb.theming.appColor
import com.tashariko.exploredb.theming.progessWidth
import com.tashariko.exploredb.theming.space14
import com.tashariko.exploredb.theming.space18
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LandingActivity : BaseActivity() {

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
            Scaffold {
                AppCompose {
                    ScreenContent(splashViewModel)
                }
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


    /**
     * Convert in ConstraintLayout
     * https://github.com/skydoves/disneycompose/blob/main/app/src/main/java/com/skydoves/disneycompose/ui/posters/Posters.kt
     * https://developer.android.com/jetpack/compose/layout#constraints
     */
    viewModelState.value?.let { state ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    text = LocalContext.current.getString(R.string.app_name),
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(space14))

                when (state.status) {
                    ApiResult.Status.LOADING -> {
                        CircularProgressIndicator(
                            Modifier.align(Alignment.CenterHorizontally),
                            color = MaterialTheme.appColor.primaryLight,
                            strokeWidth = progessWidth
                        )
                    }
                    ApiResult.Status.ERROR -> {
                        state.errorType?.let { et ->
                            if (et.type == ErrorType.Type.Generic) {
                                (LocalContext.current as LandingActivity).showToast(
                                    LocalContext.current.getString(
                                        R.string.generic_error_message
                                    )
                                )
                            } else {
                                et.message?.let { msg ->
                                    (LocalContext.current as LandingActivity).showToast(msg)
                                } ?: run {

                                }
                            }
                        } ?: run {
                            (LocalContext.current as LandingActivity).showToast(
                                LocalContext.current.getString(
                                    R.string.generic_error_message
                                )
                            )
                        }
                        Button(onClick = {

                        }, content = { Text(text = "Button") },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.appColor.primaryLight,
                            )
                        )
                    }
                    ApiResult.Status.SUCCESS -> {
                        MainActivity.launchScreen(LocalContext.current)
                    }
                }
            }
        }
    }

}