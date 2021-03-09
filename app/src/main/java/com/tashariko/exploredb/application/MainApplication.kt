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
import com.tashariko.exploredb.di.injectable.AppInjector
import com.tashariko.exploredb.di.util.HasWorkerInjector
import com.tashariko.exploredb.util.NetworkObserver
import com.tashariko.exploredb.util.SingleLiveEvent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainApplication: MultiDexApplication(), HasActivityInjector, HasServiceInjector, HasWorkerInjector {


    @Inject
    lateinit var workerDispatchingAndroidInjector: DispatchingAndroidInjector<Worker>

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var dispatchingServiceInjector: DispatchingAndroidInjector<Service>

    @Inject
    lateinit var androidWorkerInjector: DispatchingAndroidInjector<CoroutineWorker>



    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
            Timber.plant(Timber.DebugTree())
        }
        Timber.i("Application Initialized")

        AppInjector.init(this)
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

    override fun serviceInjector(): AndroidInjector<Service> = dispatchingServiceInjector

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

    override fun workerInjector(): AndroidInjector<CoroutineWorker> = androidWorkerInjector

}