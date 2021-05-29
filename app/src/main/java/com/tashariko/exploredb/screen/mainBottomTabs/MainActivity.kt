package com.tashariko.exploredb.screen.mainBottomTabs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.tashariko.exploredb.application.base.AppCompose
import com.tashariko.exploredb.application.base.BaseActivity

class MainActivity : BaseActivity() {

    companion object {
        fun launchScreen(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
            (context as BaseActivity).finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCompose {
                MainScreenContent()
            }
        }
    }

    override fun handleIncomingIntent() {
        TODO("Not yet implemented")
    }
}