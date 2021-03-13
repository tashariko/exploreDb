package com.tashariko.exploredb.application

import android.content.Context
import android.content.Intent
import com.tashariko.exploredb.application.base.BaseActivity
import com.readystatesoftware.chuck.internal.ui.MainActivity
import com.tashariko.exploredb.screen.splash.LandingActivity

object ActivityLauncher  {

    fun openMainScreen(context: Context) {
        val mainIntent = Intent(context, MainActivity::class.java)
        context.startActivity(mainIntent)
        (context as BaseActivity).finish()
    }

    fun openLandingScreen(context: Context) {
        val mainIntent = Intent(context, LandingActivity::class.java)
        context.startActivity(mainIntent)
        (context as BaseActivity).finish()
    }
}