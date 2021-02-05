package com.tashariko.exploredb.ui.splash

import android.os.Bundle
import com.tashariko.exploredb.application.ActivityLauncher
import com.tashariko.exploredb.application.AppConstants
import com.tashariko.exploredb.application.base.BaseActivity
import com.tashariko.exploredb.util.SharedPreferenceHelper

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityLauncher.openLandingScreen(this)

    }

    override fun handleIncomingIntent() {

    }

    override fun bindAndSetupUI() {

    }

    override fun vmListeners() {

    }

    override fun viewlisteners() {

    }
}