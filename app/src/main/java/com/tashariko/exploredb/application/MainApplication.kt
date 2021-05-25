package com.tashariko.exploredb.application

import android.app.Activity
import android.app.Service
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.multidex.MultiDexApplication
import androidx.work.*
import com.facebook.stetho.Stetho
import com.tashariko.exploredb.BuildConfig
import com.tashariko.exploredb.util.NetworkObserver
import com.tashariko.exploredb.util.SingleLiveEvent
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MainApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
            Timber.plant(Timber.DebugTree())
        }
        Timber.i("Application Initialized")

    }
//
//
//    public fun setNetObserver(owner: LifecycleOwner): SingleLiveEvent<Boolean> {
//
//        var netListener = SingleLiveEvent<Boolean>()
//
//        NetworkObserver.getNetLiveData(this).asLiveData().observe(owner, Observer {
//            netListener.postValue(it)
//        })
//
//        return netListener
//    }
}