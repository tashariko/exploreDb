package com.tashariko.exploredb.screen.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import com.tashariko.exploredb.application.base.AppCompose
import com.tashariko.exploredb.application.base.BaseActivity
import com.tashariko.exploredb.screen.home.mainNav.MainScreenContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                MainScreenContent(languageChanged = {
                    Toast.makeText(this, "In Progress", Toast.LENGTH_SHORT).show()
                })
            }
        }
    }

    override fun handleIncomingIntent() {
        TODO("Not yet implemented")
    }
}