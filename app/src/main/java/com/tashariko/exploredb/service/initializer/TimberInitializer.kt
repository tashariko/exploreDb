package com.tashariko.exploredb.service.initializer

import android.content.Context
import androidx.startup.Initializer
import com.tashariko.exploredb.BuildConfig
import timber.log.Timber

class TimberInitializer: Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        Timber.i("Timber Initialized")
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}